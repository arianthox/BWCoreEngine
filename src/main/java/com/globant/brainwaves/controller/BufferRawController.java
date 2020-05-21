package com.globant.brainwaves.controller;

import com.globant.brainwaves.domain.BufferRawData;
import com.globant.brainwaves.model.BufferRawPacket;
import com.globant.brainwaves.service.PacketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/core-engine/packet/raw")
public class BufferRawController implements com.globant.brainwaves.controller.api.BufferRawController {

    private final transient PacketService packetService;

    public BufferRawController(PacketService packetService) {
        this.packetService = packetService;
    }

    @PostMapping("/receive/{deviceId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId,@RequestBody @Valid BufferRawPacket... packet) {
        packetService.send(deviceId,packet);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive/{deviceId}/{sessionId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId,@PathVariable("sessionId") String sessionId,@RequestBody @Valid BufferRawPacket... packet) {
        packetService.send(deviceId,sessionId,packet);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{deviceId}")
    public ResponseEntity<List<BufferRawData>> find(@PathVariable("deviceId") String deviceId) {
        return ResponseEntity.of(packetService.findByDeviceId(deviceId));
    }



}