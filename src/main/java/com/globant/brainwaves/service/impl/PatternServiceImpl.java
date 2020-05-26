package com.globant.brainwaves.service.impl;

import com.globant.brainwaves.commons.adapter.KafkaProducer;
import com.globant.brainwaves.domain.PatternFileData;
import com.globant.brainwaves.dto.PatternFileInfoDTO;
import com.globant.brainwaves.repository.BufferRawRepository;
import com.globant.brainwaves.repository.PatternFileRepository;
import com.globant.brainwaves.service.PacketService;
import com.globant.brainwaves.service.PatternService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Log
@Service
public class PatternServiceImpl implements com.globant.brainwaves.service.PatternService {

        private Logger logger = Logger.getLogger(com.globant.brainwaves.service.PatternService.class.getName());
        private PatternFileRepository patternFileRepository;


        public PatternServiceImpl(PatternFileRepository patternFileRepository) {
            this.patternFileRepository = patternFileRepository;
        }



        @Override
    public void savePattern(PatternFileInfoDTO pfi) {

            PatternFileData pfd= PatternFileData.builder()
                    .data(pfi.getContent())
                    .name(pfi.getName())
                    .build();
            logger.info("CONTENT //////////////////////////////////////////");
            logger.info(pfi.content);
            logger.info("CONTENT //////////////////////////////////////////");

            pfd.setData(pfi.getContent());
            pfd.setName(pfi.getName());
            patternFileRepository.save(pfd);
    }

    @Override
    public PatternFileInfoDTO getPattern(String name) {
        return null;
    }
}
