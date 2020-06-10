package com.globant.brainwaves.controller.api;

import com.globant.brainwaves.domain.PatternFileData;
import com.globant.brainwaves.dto.PatternFileInfoDTO;
import com.globant.brainwaves.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CoreEngine")
public interface PatternController {
    @GetMapping("/gettesting/{name}")
    ResponseEntity<ResponseDTO<PatternFileData>> find(@PathVariable("name") String name);

    @PostMapping("/pattern")
    ResponseEntity<ResponseDTO<String>> savePattern(@RequestBody PatternFileInfoDTO pattern);

    @GetMapping("/pattern/{type}")
    ResponseEntity<ResponseDTO<List<PatternFileInfoDTO>>> findByType(@PathVariable("type") String type);

    }
