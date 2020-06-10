package com.globant.brainwaves.service.impl;

import com.globant.brainwaves.domain.PatternFileData;
import com.globant.brainwaves.dto.PatternFileInfoDTO;
import com.globant.brainwaves.repository.PatternFileRepository;
import com.google.gson.Gson;
import com.sun.jersey.api.NotFoundException;
import feign.FeignException;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
@Service
@Data
public class PatternServiceImpl implements com.globant.brainwaves.service.PatternService {

        private static final Logger logger = Logger.getLogger(PatternServiceImpl.class.getName());
        private final PatternFileRepository patternFileRepository;


        public PatternServiceImpl(PatternFileRepository patternFileRepository) {
            this.patternFileRepository = patternFileRepository;
        }


        @Override
    public void savePattern(PatternFileInfoDTO pfi) {
            log.info("Into savePattern...");
            PatternFileData pfd= PatternFileData.builder()
                    .data(pfi.getContent())
                    .name(pfi.getName())
                    .type(pfi.getType())
                    .build();
            logger.info("Content type..." + pfi.getType());
            patternFileRepository.save(pfd);
    }

    @Override
    public List<PatternFileInfoDTO> getFilesByType(String typeName) {
            Optional<List<PatternFileData>> filesList =
                    patternFileRepository.findAllByType(typeName);
            if(filesList.get().isEmpty()){
                return null;
            }
            return filesList.get().stream().map(p -> new PatternFileInfoDTO(p.getName(), p.getData(),
                    p.getType(), p.getId())).collect(Collectors.toList());
    }

    @Override
    public PatternFileData getPatternByName(String name){
            Optional<PatternFileData> patternFileData = patternFileRepository.findByName(name);
            if(!patternFileData.isPresent()){
                throw new FeignException.BadRequest("Not found..", null, null);
            }
            return patternFileData.get();
    }
}
