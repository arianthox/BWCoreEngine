package com.globant.brainwaves.service;

import com.globant.brainwaves.domain.PatternFileData;
import com.globant.brainwaves.dto.PatternFileInfoDTO;
import com.google.gson.Gson;

import java.util.List;

public interface PatternService {

    void savePattern(PatternFileInfoDTO pfi);

    List<PatternFileInfoDTO> getFilesByType(String type);

    PatternFileData getPatternByName(String name);

}
