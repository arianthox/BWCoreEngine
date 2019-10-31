package com.globant.brainwaves.controller;

import com.globant.brainwaves.model.Wave;
import com.globant.brainwaves.service.WaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/core-engine")
public class WaveController {

    @Autowired
    private WaveService waveService;

    @GetMapping
    public String sayHello() {
        return "Hello Wave !!!";
    }

    @GetMapping("{id}")
    @ResponseBody
    public Wave waveById(@PathVariable("id") String id) {
        return waveService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWave(@Valid @RequestBody Wave wave) {
        waveService.save(wave);
    }

}
