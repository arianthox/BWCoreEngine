package com.globant.brainwaves.service;

import com.globant.brainwaves.commons.adapter.KafkaProducer;
import com.globant.brainwaves.commons.model.BufferRawPacket;
import com.globant.brainwaves.commons.persistence.elastic.domain.BufferRawData;
import com.globant.brainwaves.commons.persistence.elastic.repository.BufferRawPacketRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class BufferRawPacketService {


    private final transient BufferRawPacketRepository packetRepository;

    private final transient KafkaProducer kafkaProducer;


    public BufferRawPacketService(BufferRawPacketRepository packetRepository, KafkaProducer kafkaProducer) {
        this.packetRepository = packetRepository;
        this.kafkaProducer = kafkaProducer;
    }


    public void send(String deviceId, String sessionId, final BufferRawPacket packet) {
        log.info(String.format("[%s] [%s] [%s] - %s", packet.getClass().getSimpleName(),deviceId, sessionId, Collections.singletonList(packet.toHashMap()).toString()));
        kafkaProducer.send(packet, done -> {
            log.fine("Saving to Repo:"+packet.toData());
            packetRepository.save(packet.toData());
            log.fine("Message sent to broker");
        });

    }

    public void send(String deviceId, final BufferRawPacket... packetList) {
        List.of(packetList).stream().forEach(bufferRawPacket -> send(deviceId, null, bufferRawPacket));
    }

    public void send(String deviceId, String sessionId, final BufferRawPacket... packetList) {
        List.of(packetList).stream().forEach(bufferRawPacket -> send(deviceId, sessionId, bufferRawPacket));
    }


    public Optional<List<BufferRawData>> findByDeviceId(String deviceId) {
        return packetRepository.findAllByDeviceId(deviceId);
    }
}
