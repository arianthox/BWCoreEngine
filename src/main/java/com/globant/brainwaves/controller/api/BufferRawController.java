package com.globant.brainwaves.controller.api;

import com.globant.brainwaves.domain.BufferRawData;
import com.globant.brainwaves.model.BufferRawPacket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "CoreEngine")
public interface BufferRawController {

    @PostMapping("/receive/{deviceId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId, @RequestBody @Valid BufferRawPacket... packet);

    @PostMapping("/receive/{deviceId}/{sessionId}")
    public ResponseEntity receive(@PathVariable("deviceId") String deviceId,@PathVariable("sessionId") String sessionId,@RequestBody @Valid BufferRawPacket... packet);

    @GetMapping("/find/{deviceId}")
    public ResponseEntity<List<BufferRawData>> find(@PathVariable("deviceId") String deviceId);

}
