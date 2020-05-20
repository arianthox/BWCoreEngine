package com.globant.brainwaves.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public class TestController  implements com.globant.brainwaves.controller.api.TestController {

    @GetMapping("/gettesting")
    public ResponseEntity<String> receive(@PathVariable("name") String name){
        return ResponseEntity.ok().build();
    }

}
