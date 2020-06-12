package com.globant.brainwaves.controller;

import com.globant.brainwaves.commons.persistence.elastic.domain.ChannelData;
import com.globant.brainwaves.commons.model.ChannelPacket;
import com.globant.brainwaves.service.ChannelPacketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/core-engine/packet/channel")
public class ChannelPacketController {

    private final transient ChannelPacketService packetService;

    public ChannelPacketController(ChannelPacketService packetService) {
        this.packetService = packetService;
    }

    @PostMapping("/receive/{deviceId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId,@RequestBody @Valid ChannelPacket... packet) {
        packetService.send(deviceId,packet);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive/{deviceId}/{sessionId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId,@PathVariable("sessionId") String sessionId,@RequestBody @Valid ChannelPacket... packet) {
        packetService.send(deviceId,sessionId,packet);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{deviceId}")
    public ResponseEntity<List<ChannelData>> find(@PathVariable("deviceId") String deviceId) {
        return ResponseEntity.of(packetService.findByDeviceId(deviceId));
    }




}