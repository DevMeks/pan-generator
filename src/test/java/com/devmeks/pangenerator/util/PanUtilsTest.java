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
class PanUtilsTest {

  @Container
  public static PostgreSQLContainer<PanRepoPostgresqlContainer> postgreSQLContainer = PanRepoPostgresqlContainer.getInstance();

  @Autowired
  private PanUtils panUtils;


  @Test
  void testRetrieveIin() {

    assertEquals("111111", panUtils.retrieveIin("Verve"));


  }

  @Test
  void testGenerateRandomDigits() {
    assertNotNull(panUtils.generateRandomNumbers(10));


  }

  @Test
  void testGenerateChecksumDigit() {

    assertEquals("6", panUtils.generateLuhnCheckDigit("111111555555555"));


  }

  @Test
  void testIsValidMobileNumber() {

    assertTrue(panUtils.isMobileNumberValid("08011111111"));
  }

  @Test
  void testIsNotValidMobileNumber() {
    assertFalse(panUtils.isMobileNumberValid("1111111111"));
  }

  @Test
  void testProcessResponse() {

    var responseDto = ResponseDto
        .builder()
        .pan("2222222222222222")
        .responseStatus(ResponseStatus.SUCCESSFUL)
        .build();


    assertEquals("ResponseEntity", panUtils.processResponse(Mono.just(responseDto))
        .getClass().getSimpleName());
  }


  @Test
  void testGenerateTransactionId(){
    assertNotNull(panUtils.generateTransactionId());
  }
}