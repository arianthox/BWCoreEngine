package com.globant.brainwaves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatternFileInfoDTO {

    public String name;
    public String  content;
    public String type;

}
