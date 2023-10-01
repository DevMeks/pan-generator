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
    void retrieveIin() {

        assertEquals("111111",panUtils.retrieveIin("Verve"));


    }

    @Test
    void generateRandomDigits() {
        assertNotNull(panUtils.generateRandomDigits());


    }

    @Test
    void generateChecksumDigit() {

        assertEquals("6", panUtils.generateChecksumDigit("111111555555555"));


    }
}