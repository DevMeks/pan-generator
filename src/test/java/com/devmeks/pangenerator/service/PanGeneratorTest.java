package com.devmeks.pangenerator.service;

import com.devmeks.pangenerator.model.request.CreatePANFromMobileNumDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PanGeneratorTest {

    @Autowired
    PanGenerator panGenerator;

    @Test
    void createPanFromMobileNumber() {

        CreatePANFromMobileNumDto createPANFromMobileNumDto = CreatePANFromMobileNumDto
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

        CreatePANFromMobileNumDto createPANFromMobileNumDto = CreatePANFromMobileNumDto
                .builder()
                        .mobileNumber("11111111111")
                                .cardScheme("verve")
                                        .build();

        assertNotNull(Objects.requireNonNull(panGenerator.generateRandomPan(createPANFromMobileNumDto)
                .block()).getPan());
    }
}