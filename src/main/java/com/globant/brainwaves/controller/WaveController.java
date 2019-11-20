package com.globant.brainwaves.controller;

import com.globant.brainwaves.model.Wave;
import com.globant.brainwaves.service.WaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/core-engine")
public class WaveController {

    @Autowired
    private transient WaveService waveService;

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

    @GetMapping
    @ResponseBody
    public List<Wave> wavesById(@RequestParam(value = "deviceId") String deviceId) {
        return waveService.findAllByDeviceId(deviceId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Wave saveWave(@RequestBody @Valid Wave wave) {
        waveService.save(wave);
        return wave;
    }

}
