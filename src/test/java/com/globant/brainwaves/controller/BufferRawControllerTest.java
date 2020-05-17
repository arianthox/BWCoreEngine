package com.globant.brainwaves.controller;

import com.globant.brainwaves.CoreEngineApplication;
import com.globant.brainwaves.domain.BufferRawData;
import com.globant.brainwaves.model.BufferRawPacket;
import com.globant.brainwaves.model.Packet;
import com.globant.brainwaves.service.PacketService;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreEngineApplication.class)
@ActiveProfiles(profiles = "mock")
@AutoConfigureMockMvc
public class BufferRawControllerTest {

    private static final String API_ENDPOINT = "/api/core-engine/packet/raw/";

    private static final String API_RECEIVE_METHOD = "receive/";

    private static final String API_FIND_METHOD = "find/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PacketService packetService;

    private static final String DEVICE_ID="1";


    @Test
    public void receiveTest() throws Exception {
        BufferRawPacket bufferRawPacket=createPacket();
        BufferRawData bufferRawData=  BufferRawData.builder()
                .bufferRawEeg(Arrays.stream(bufferRawPacket.getBufferRawEeg()).boxed().collect(Collectors.toList()))
                .deviceId(DEVICE_ID).build();

        when(packetService.findByDeviceId(DEVICE_ID)).thenReturn(Optional.of(List.of(bufferRawData)));

        mvc.perform(MockMvcRequestBuilders.post(String.format("%s%s%s", API_ENDPOINT, API_RECEIVE_METHOD,DEVICE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(Arrays.asList(bufferRawPacket)))
                )
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get(String.format("%s%s%s", API_ENDPOINT, API_FIND_METHOD, DEVICE_ID)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(List.of(bufferRawData)), false));

    }


    @NotNull
    private BufferRawPacket createPacket() {
        BufferRawPacket packet = new BufferRawPacket();
        packet.setBufferRawEeg(IntStream.range(1,512).map(x->getRandomNumberInt(-2047+x,2047-x)).toArray());
        return packet;
    }

    private static int getRandomNumberInt(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }


    private String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}