package com.devmeks.pangenerator.service;

import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Testcontainers
class PanGeneratorTest {

  @Container
  private static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:11.1")
      .withDatabaseName("integration-tests-db")
          .withUsername("username")
          .withPassword("password");

  static {
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @Autowired
  PanGenerator panGenerator;



  @Test
  void createPanFromMobileNumber() {

    CreatePanFromMobileNumDto createPANFromMobileNumDto = CreatePanFromMobileNumDto
        .builder()
        .mobileNumber("11111111111")
        .cardScheme("verve")
        .build();

    assertEquals("1111111111111117", Objects.requireNonNull(panGenerator
            .createPanFromMobileNumber(createPANFromMobileNumDto)
            .block())
        .getPan());


  }

  @Test
  void generateRandomPan() {

    CreatePanFromMobileNumDto createPANFromMobileNumDto = CreatePanFromMobileNumDto
        .builder()
        .mobileNumber("11111111111")
        .cardScheme("verve")
        .build();

    assertNotNull(Objects.requireNonNull(panGenerator.generateRandomPan(createPANFromMobileNumDto)
        .block()).getPan());
  }


}