package com.globant.brainwaves.service;

import com.globant.brainwaves.dto.PatternFileInfoDTO;

public interface PatternService {

    void savePattern(PatternFileInfoDTO pfi);

    PatternFileInfoDTO getPattern(String name);

}
