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
import java.util.stream.Collectors;

@Log
@Service
public class PatternServiceImpl implements PatternService {

        private final PatternFileRepository patternFileRepository;


        public PatternServiceImpl(PatternFileRepository patternFileRepository) {
            this.patternFileRepository = patternFileRepository;
        }


        @Override
    public void savePattern(PatternFileInfoDTO pfi) {
            log.info("Into savePattern...");
            PatternFileData pfd= PatternFileData.builder()
                    .data(pfi.content)
                    .name(pfi.name)
                    .type(pfi.type)
                    .build();
            log.info("Content type... " + pfi.type);
            patternFileRepository.save(pfd);
    }

    @Override
    public List<PatternFileInfoDTO> getFilesByType(String typeName) {
            log.info("getFilesByType");
            Optional<List<PatternFileData>> filesList =
                    patternFileRepository.findByType(typeName);
            if(filesList.get().isEmpty()){
                return null;
            }
            log.info(" antes de return getFilesByType");
            log.info("resultado: " + filesList.get().size());
            log.info("resultado: " + filesList.get().get(1).getName());
            List<PatternFileData> data = filesList.get();
            data.forEach(p-> new PatternFileInfoDTO(p.getName(), p.getData(),
                    p.getType()));

            List<PatternFileInfoDTO> result = data.stream().map(temp -> {
                PatternFileInfoDTO obj =
                        new PatternFileInfoDTO(temp.getName(), temp.getData(), temp.getType());
            return obj;
             }).collect(Collectors.toList());
            return result;
    }

    @Override
    public PatternFileInfoDTO getPatternByName(String name){
            Optional<PatternFileData> patternFileData = patternFileRepository.findByName(name);
            if(!patternFileData.isPresent()){
                throw new FeignException.BadRequest("Not found..", null, null);
            }
            PatternFileData data = patternFileData.get();
            return  PatternFileInfoDTO.builder().content(data.getData()).name(data.getName())
                    .type(data.getType()).build();
    }

    @Override
    public String remove(String id){
        patternFileRepository.deleteById(id);
        return "OK";
    }
}
