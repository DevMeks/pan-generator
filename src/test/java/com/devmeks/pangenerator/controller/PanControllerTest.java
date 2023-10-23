package com.devmeks.pangenerator.controller;

import com.devmeks.pangenerator.container.PanRepoPostgresqlContainer;
import com.devmeks.pangenerator.service.PanGenerator;
import com.devmeks.pangenerator.util.PanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@WebMvcTest(PanController.class)
@ActiveProfiles("test")
@Testcontainers
class PanControllerTest {
  @Container
  public static PostgreSQLContainer<PanRepoPostgresqlContainer> postgreSQLContainer
      = PanRepoPostgresqlContainer.getInstance();


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PanGenerator panGenerator;

  @MockBean
  private PanUtils panUtils;

  @Test
  void createPan() throws Exception {
    String requestBody = "{\"mobileNumber\": \"11111111111\", \"cardScheme\": \"verve\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pan/generate-pan")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(""));


  }


  @Test
  void createPanInvalidMobileNumber() throws Exception {
    String requestBody = "{\"mobileNumber\": \"\", \"cardScheme\":  \"verve\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pan/generate-pan")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON));
  }
}