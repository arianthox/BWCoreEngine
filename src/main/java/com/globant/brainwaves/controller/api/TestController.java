package com.globant.brainwaves.controller.api;

import com.globant.brainwaves.model.BufferRawPacket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "CoreEngine")
public interface TestController {

        @PostMapping("/test")
        public ResponseEntity<String> receive(@PathVariable("name") String name);

    }
