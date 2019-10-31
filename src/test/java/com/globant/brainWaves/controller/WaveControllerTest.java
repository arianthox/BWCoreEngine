package com.globant.brainWaves.controller;

import com.globant.brainwaves.CoreEngineApplication;
import com.globant.brainwaves.model.Wave;
import com.globant.brainwaves.service.WaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreEngineApplication.class)
@AutoConfigureMockMvc
public class WaveControllerTest {

    private static final String API_CORE_ENGINE = "/api/core-engine";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WaveService waveService;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_CORE_ENGINE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello Wave !!!")));
    }

    @Test
    public void waveById() throws Exception {

        Wave wave = new Wave();
        wave.setId("1");
        wave.setName("wave");
        when(waveService.findById("1")).thenReturn(Optional.of(wave));

        mvc.perform(MockMvcRequestBuilders.get(API_CORE_ENGINE+"/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(wave),false));

    }


    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


}