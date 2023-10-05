package com.devmeks.pangenerator.controller;

import com.devmeks.pangenerator.service.PanGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PanController.class)
class PanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PanGenerator panGenerator;

    @Test
    void createPan() throws Exception {
        String requestBody = "{\"mobileNumber\": \"11111111111\", \"cardScheme\": \"verve\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pan/generate-pan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));



    }
}