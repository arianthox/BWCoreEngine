package com.globant.brainwaves.service;

import com.globant.brainwaves.commons.adapter.KafkaProducer;
import com.globant.brainwaves.domain.BufferRawData;
import com.globant.brainwaves.model.BufferRawPacket;
import com.globant.brainwaves.repository.BufferRawRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
@Service
public class PacketService {

    private Logger logger = Logger.getLogger(PacketService.class.getName());

    private final BufferRawRepository bufferRawRepository;

    private final KafkaProducer kafkaProducer;


    public PacketService(BufferRawRepository bufferRawRepository, KafkaProducer kafkaProducer) {
        this.bufferRawRepository = bufferRawRepository;
        this.kafkaProducer = kafkaProducer;
    }


    public void send(String deviceId, String sessionId, final BufferRawPacket packet) {
        logger.info(String.format("Device: %s SessionId: %s Packet: %s", deviceId, sessionId, Collections.singletonList(packet.toHashMap()).toString()));

        BufferRawData bufferRawData = BufferRawData.builder()
                .deviceId(deviceId)
                .sessionId(sessionId)
                .bufferRawEeg((Arrays.stream(packet.getBufferRawEeg()).boxed().collect(Collectors.toList())))
                .build();

        kafkaProducer.send(bufferRawData, done -> {
            bufferRawRepository.save(bufferRawData);
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
        return bufferRawRepository.findAllByDeviceId(deviceId);
    }
}
