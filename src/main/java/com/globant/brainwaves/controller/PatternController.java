package com.globant.brainwaves.controller;

import com.globant.brainwaves.commons.model.PatternFileInfoDTO;
import com.globant.brainwaves.commons.model.ResponseDTO;
import com.globant.brainwaves.service.PatternService;
import feign.FeignException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/io")
@Log
public class PatternController {

    private final transient PatternService patternService;

    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @GetMapping("/pattern/name/{name}")
    public ResponseEntity<ResponseDTO<PatternFileInfoDTO>> findByName(@PathVariable("name") String name) {

        try {
            if (name == null || name.equals("")) {
                log.info(HttpStatus.BAD_REQUEST.toString());
                throw new FeignException.BadRequest("Not valid name...", null, null);
            }
            PatternFileInfoDTO pfd = patternService.getPatternByName(name);
            ResponseDTO<PatternFileInfoDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(HttpStatus.OK.name());
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setContent(pfd);
            return ResponseEntity.ok().body(responseDTO);
        } catch (FeignException.BadRequest br) {
            ResponseDTO<PatternFileInfoDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(br.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/pattern")
    public ResponseEntity<ResponseDTO<String>> savePattern(@RequestBody PatternFileInfoDTO pattern) {

        try {
            if (pattern == null) {
                log.info(HttpStatus.BAD_REQUEST.toString());
                throw new FeignException.BadRequest("Not valid parameters...", null, null);
            }
            patternService.savePattern(pattern);
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(HttpStatus.OK.name());
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDTO);
        } catch (FeignException.BadRequest br) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(br.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/pattern/type/{type}")
    public ResponseEntity<ResponseDTO<List<PatternFileInfoDTO>>>
    findByType(@PathVariable("type") String type) throws FeignException.BadRequest {

        try {
            if (type.isBlank()) {
                log.info("Blank type " + HttpStatus.BAD_REQUEST.toString());
                throw new FeignException.BadRequest("Not valid parameters...", null, null);
            }
            List<PatternFileInfoDTO> fileList = patternService.getFilesByType(type);
            ResponseDTO<List<PatternFileInfoDTO>> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(HttpStatus.OK.name());
            responseDTO.setContent(fileList);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDTO);
        } catch (FeignException.BadRequest br) {
            ResponseDTO<List<PatternFileInfoDTO>> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(br.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @DeleteMapping(value = "/pattern/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            if (id.isBlank()) {
                log.info("Blank id request " + HttpStatus.BAD_REQUEST.toString());
                throw new FeignException.BadRequest("Not valid parameters...", null, null);
            }
            String response = patternService.remove(id);
            return ResponseEntity.ok().body(response);
        } catch (FeignException.BadRequest br) {
            ResponseDTO<PatternFileInfoDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(br.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST.name());
        }
    }

}
