package com.globant.brainwaves.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "pattern_file_data")
public class PatternFileData implements Serializable {

    @Id
    private String id;

    private String name;

    private String type;

    private String data;

}
