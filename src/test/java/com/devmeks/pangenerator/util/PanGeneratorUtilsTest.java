package com.devmeks.pangenerator.util;

import com.devmeks.pangenerator.container.PanRepoPostgresqlContainer;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class PanGeneratorUtilsTest {

  @Container
  public static PostgreSQLContainer<PanRepoPostgresqlContainer> postgreSQLContainer = PanRepoPostgresqlContainer.getInstance();

  @Autowired
  private PanGeneratorUtils panGeneratorUtils;


  @Test
  void testRetrieveIin() {

    assertEquals("111111", panGeneratorUtils.retrieveIin("Verve"));


  }

  @Test
  void testGenerateRandomDigits() {
    assertNotNull(panGeneratorUtils.generateRandomNumbers(10));


  }

  @Test
  void testGenerateChecksumDigit() {

    assertEquals("6", panGeneratorUtils.generateLuhnCheckDigit("111111555555555"));


  }

  @Test
  void testIsValidMobileNumber() {

    assertTrue(panGeneratorUtils.isMobileNumberValid("08011111111"));
  }

  @Test
  void testIsNotValidMobileNumber() {
    assertFalse(panGeneratorUtils.isMobileNumberValid("1111111111"));
  }

  @Test
  void testProcessResponse() {

    var responseDto = ResponseDto
        .builder()
        .pan("2222222222222222")
        .responseStatus(ResponseStatus.SUCCESSFUL)
        .build();


    assertEquals("ResponseEntity", panGeneratorUtils.processResponse(Mono.just(responseDto))
        .getClass().getSimpleName());
  }


  @Test
  void testGenerateTransactionId() {
    assertNotNull(panGeneratorUtils.generateTransactionId());
  }
}