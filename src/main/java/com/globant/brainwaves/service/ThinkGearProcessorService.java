package com.globant.brainwaves.service;


import akka.Done;
import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.globant.brainwaves.commons.adapter.KafkaConsumer;
import com.globant.brainwaves.commons.adapter.KafkaProducer;
import com.globant.brainwaves.commons.model.ConsumerID;
import com.globant.brainwaves.commons.model.TopicID;
import com.globant.brainwaves.commons.model.WavePacket;
import com.globant.brainwaves.commons.model.WaveTrainingPacket;
import com.globant.brainwaves.stream.WaveTrainingPacketGraphStage;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;

import static akka.stream.Supervision.*;

@Component
@Log
public class ThinkGearProcessorService {

    private final transient KafkaProducer kafkaProducer;

    private final transient Gson gson;

    final static Function<Throwable, Directive> decider =
            exc -> {
                if (exc instanceof Exception) {
                    log.log(Level.SEVERE, exc.getMessage());
                    return resume();
                } else {
                    log.log(Level.SEVERE, "Exception:" + exc.getMessage());
                    return stop();
                }
            };
    private final transient KafkaConsumer kafkaConsumer;
    private final transient ActorSystem system;

    public ThinkGearProcessorService(KafkaConsumer kafkaConsumer, KafkaProducer kafkaProducer, Gson gson) {
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
        this.gson = gson;
        system = ActorSystem.create(Behaviors.empty(), "kafka-system");
    }

    @PostConstruct
    private void initialize() {
        log.info("Initializing Kafka Consumer");
        WaveTrainingPacketGraphStage waveTrainingPacketGraphStage = new WaveTrainingPacketGraphStage();

        this.kafkaConsumer.consume(ConsumerID.CORE_ENGINE, TopicID.THINK_GEAR_READER.setConsumerProcess(record -> {
            final Source<WaveTrainingPacket, NotUsed> flow = Source.single(record)
                    .map(param -> gson.fromJson(param.value(), WavePacket.class))
                    .via(waveTrainingPacketGraphStage)
                    .withAttributes(ActorAttributes.withSupervisionStrategy(decider));
            final Sink<WaveTrainingPacket, CompletionStage<Done>> sink = Sink.foreach(packet -> {
                kafkaProducer.send(TopicID.MATCHER_TRAINER.setProducerAction(done -> log.log(Level.INFO, "Packet - {0}", Arrays.asList(packet.getHeader().getPacket().getClass().getSimpleName(), packet.getBuffer().size()))), packet);
            });

            return flow.runWith(sink, system);

        }));

    }
}
