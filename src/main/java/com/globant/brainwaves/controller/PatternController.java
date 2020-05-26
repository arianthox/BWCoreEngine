package com.globant.brainwaves.controller;

import com.globant.brainwaves.dto.PatternFileInfoDTO;
import com.globant.brainwaves.service.PatternService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
@Log
public class PatternController {

    private final PatternService patternService;

    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @GetMapping("/gettesting/{name}")
    public ResponseEntity<String> find(@PathVariable("name") String name){
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/pattern")
    public ResponseEntity<String> savePattern(@RequestBody PatternFileInfoDTO pattern){
        if(pattern == null){
        log.info("ERROR");
        }
        patternService.savePattern(pattern);

        return ResponseEntity.accepted().build();
    }

}
