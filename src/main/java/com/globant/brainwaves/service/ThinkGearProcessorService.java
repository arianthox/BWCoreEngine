package com.globant.brainwaves.service;


import akka.Done;
import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.Supervision;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.globant.brainwaves.commons.adapter.KafkaConsumer;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;

@Component
@Log
public class ThinkGearProcessorService {

    private static final Gson gson = new Gson();
    final Function<Throwable, Supervision.Directive> decider =
            exc -> {
                if (exc instanceof Exception) {
                    log.log(Level.WARNING, "Error Parsing Class");
                    return Supervision.resume();
                } else {
                    log.log(Level.SEVERE, "Exception:"+exc.getMessage());
                    return Supervision.stop();
                }
            };
    private final KafkaConsumer kafkaConsumer;
    private ActorSystem system;

    public ThinkGearProcessorService(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
        system = ActorSystem.create(Behaviors.empty(), "kafka-system");
    }

    @PostConstruct
    private void initialize() {
        log.info("Initializing Kafka Consumer");
        this.kafkaConsumer.consume("core-engine","think-gear-topic",record -> {

            final Source<String, NotUsed> flow = Source.single(record)
                    .map(param -> param.value())
                    .withAttributes(ActorAttributes.withSupervisionStrategy(decider));


            final Sink<String, CompletionStage<Done>> sink = Sink.foreach(param -> {
                log.log(Level.INFO, "Packet: {0}", param);
            });


            return flow.runWith(sink, system);

        });

    }
}
