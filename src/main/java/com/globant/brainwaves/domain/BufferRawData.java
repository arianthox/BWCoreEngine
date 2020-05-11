package com.globant.brainwaves.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "buffer_raw_data")
public class BufferRawData implements Serializable {

    private static final long serialVersionUID = 2020128099084761251L;

    @Id
    private String id;

    @NotBlank(message = "Device Id is mandatory")
    private String deviceId;

    private List<Integer> bufferRawEeg;

}


