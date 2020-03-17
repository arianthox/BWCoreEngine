package com.globant.brainwaves.controller;

import com.globant.brainwaves.model.BufferRawPacket;
import com.globant.brainwaves.service.PacketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/core-engine/packet/bufferrawpacket")
public class BufferRawPacketController {

    private final transient PacketService packetService;

    public BufferRawPacketController(PacketService packetService) {
        this.packetService = packetService;
    }

    @PostMapping("/receive/{deviceId}")
    public ResponseEntity receive(@PathVariable("deviceId") String id,@RequestBody @Valid BufferRawPacket... packet) {
        packetService.send(id,packet);
        return ResponseEntity.ok().build();
    }



}