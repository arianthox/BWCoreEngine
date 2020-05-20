package com.globant.brainwaves.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping("/gettesting/{name}")
    public ResponseEntity<String> find(@PathVariable("name") String name){
        return ResponseEntity.ok().build();
    }

}
