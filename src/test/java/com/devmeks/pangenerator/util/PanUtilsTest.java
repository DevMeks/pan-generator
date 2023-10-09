package com.devmeks.pangenerator.util;

import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class PanUtilsTest {

    @Autowired
    private PanUtils panUtils;


    @Test
    void testRetrieveIin() {

        assertEquals("111111",panUtils.retrieveIin("Verve"));


    }

    @Test
    void testGenerateRandomDigits() {
        assertNotNull(panUtils.generateRandomDigits());


    }

    @Test
    void testGenerateChecksumDigit() {

        assertEquals("6", panUtils.generateChecksumDigit("111111555555555"));


    }

    @Test
    void testIsValidMobileNumber() {

        assertTrue(panUtils.isValidMobileNumber("08011111111"));
    }

    @Test
    void testIsNotValidMobileNumber(){
        assertFalse(panUtils.isValidMobileNumber("1111111111"));
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
}