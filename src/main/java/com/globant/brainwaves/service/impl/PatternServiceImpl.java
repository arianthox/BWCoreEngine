package com.globant.brainwaves.service.impl;

import com.globant.brainwaves.commons.model.PatternFileInfoDTO;
import com.globant.brainwaves.commons.persistence.elastic.domain.PatternFileData;
import com.globant.brainwaves.commons.persistence.elastic.repository.PatternFileRepository;
import com.globant.brainwaves.service.PatternService;
import feign.FeignException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
@Service
public class PatternServiceImpl implements PatternService {

    private final transient PatternFileRepository patternFileRepository;


    public PatternServiceImpl(PatternFileRepository patternFileRepository) {
        this.patternFileRepository = patternFileRepository;
    }


    @Override
    public void savePattern(PatternFileInfoDTO pfi) {
        log.log(Level.FINE,"Into savePattern...");
        PatternFileData pfd = PatternFileData.builder()
                .data(pfi.content)
                .name(pfi.name)
                .type(pfi.type)
                .build();
        log.log(Level.FINE,"Content type... " + pfi.type);
        patternFileRepository.save(pfd);
    }

    @Override
    public List<PatternFileInfoDTO> getFilesByType(String typeName) {
        log.log(Level.FINE,"getFilesByType");
        Optional<List<PatternFileData>> filesList =
                patternFileRepository.findByType(typeName);
        if (filesList.get().isEmpty()) {
            return null;
        }
        return filesList.get().stream().map(temp ->
                    new PatternFileInfoDTO(temp.getName(), temp.getData(), temp.getType())
        ).collect(Collectors.toList());
    }

    @Override
    public PatternFileInfoDTO getPatternByName(String name) {
        Optional<PatternFileData> patternFileData = patternFileRepository.findByName(name);
        if (!patternFileData.isPresent()) {
            throw new FeignException.BadRequest("Not found..", null, null);
        }
        PatternFileData data = patternFileData.get();
        return PatternFileInfoDTO.builder().content(data.getData()).name(data.getName())
                .type(data.getType()).build();
    }

    @Override
    public String remove(String id) {
        patternFileRepository.deleteById(id);
        return "OK";
    }
}
