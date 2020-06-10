package com.globant.brainwaves.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatternFileInfoDTO {

    public String id;
    public String name;
    public String content;
    public String type;

}
