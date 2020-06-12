package com.globant.brainwaves.service;

import com.globant.brainwaves.domain.PatternFileData;
import com.globant.brainwaves.commons.dto.PatternFileInfoDTO;

import java.util.List;

public interface PatternService {

    void savePattern(PatternFileInfoDTO pfi);
    List<PatternFileInfoDTO> getFilesByType(String type);
    PatternFileInfoDTO getPatternByName(String name);
    String remove(String id);

}
