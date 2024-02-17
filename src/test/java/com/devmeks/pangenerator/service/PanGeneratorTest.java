package com.devmeks.pangenerator.service;

import com.devmeks.pangenerator.container.PanRepoPostgresqlContainer;
import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Testcontainers
class PanGeneratorTest {

  @Container
  public static PostgreSQLContainer<PanRepoPostgresqlContainer> postgreSQLContainer
      = PanRepoPostgresqlContainer.getInstance();

  @Autowired
  PanGenerator panGenerator;


  CreatePanFromMobileNumDto getRequestObject() {

    return CreatePanFromMobileNumDto
        .builder()
        .mobileNumber("11111111111")
        .cardScheme("verve")
        .build();

  }

  @Test
  @Order(value = 1)
  void createPanFromMobileNumber() {

    String pan = Objects.requireNonNull(panGenerator
            .createPanFromMobileNumber(getRequestObject())
            .block())
        .getPan();


    assertNotNull(pan);


  }


  @Test
  @Order(value = 2)
  void createRandomPanAfterDuplicateOccurs() {

    assertNotNull(Objects.requireNonNull(panGenerator
            .createPanFromMobileNumber(getRequestObject())
            .block())
        .getPan());

  }

  @Test
  void generateRandomPan() {

    assertNotNull(Objects.requireNonNull(panGenerator.generateRandomPan(getRequestObject().getCardScheme(), getRequestObject().isGlobalVerveCard())
        .block()).getPan());
  }


  @Test
  void getPans(){
    assertNotNull(panGenerator.getPans(1,1));
  }

}