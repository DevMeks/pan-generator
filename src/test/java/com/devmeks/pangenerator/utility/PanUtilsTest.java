package com.devmeks.pangenerator.utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
}