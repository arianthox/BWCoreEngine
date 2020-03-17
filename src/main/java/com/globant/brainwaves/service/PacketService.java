package com.globant.brainwaves.service;

import com.globant.brainwaves.model.BufferRawPacket;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class PacketService {

    private Logger logger= Logger.getLogger(PacketService.class.getName());


    public void send(String deviceId,final BufferRawPacket packet) {
        logger.info(String.format("Device: %s Packet: %s", deviceId,Collections.singletonList(packet.toHashMap()).toString()));
    }
    public void send(String deviceId,final BufferRawPacket... packetList) {
        List.of(packetList).stream().forEach(bufferRawPacket -> send(deviceId,bufferRawPacket));
    }
}
