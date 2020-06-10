package com.globant.brainwaves.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {

    private T content;
    private String message;
    private int statusCode;


}
