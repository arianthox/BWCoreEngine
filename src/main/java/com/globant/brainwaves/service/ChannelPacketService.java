package com.globant.brainwaves.service;

import com.globant.brainwaves.commons.adapter.KafkaProducer;
import com.globant.brainwaves.commons.persistence.elastic.domain.ChannelData;
import com.globant.brainwaves.commons.model.ChannelPacket;
import com.globant.brainwaves.commons.persistence.elastic.repository.ChannelPacketRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Log
@Service
public class ChannelPacketService {


    private final transient ChannelPacketRepository packetRepository;

    private final transient KafkaProducer kafkaProducer;


    public ChannelPacketService(ChannelPacketRepository packetRepository, KafkaProducer kafkaProducer) {
        this.packetRepository = packetRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void send(String deviceId, String sessionId, final ChannelPacket packet) {
        log.info(String.format("Packet[%s] Device[%s] SessionId[%s] - %s", packet.getClass().getName(),deviceId, sessionId, Collections.singletonList(packet.toHashMap()).toString()));
        kafkaProducer.send("channel",packet, done -> {
            log.fine("Message sent to broker");
        });

    }

    public void send(String deviceId, final ChannelPacket... packetList) {
        List.of(packetList).stream().forEach(channelPacket -> send(deviceId, null, channelPacket));
    }

    public void send(String deviceId, String sessionId, final ChannelPacket... packetList) {
        List.of(packetList).stream().forEach(channelPacket -> send(deviceId, sessionId, channelPacket));
    }

    public Optional<List<ChannelData>> findByDeviceId(String deviceId) {
        return packetRepository.findAllByDeviceId(deviceId);
    }
}
