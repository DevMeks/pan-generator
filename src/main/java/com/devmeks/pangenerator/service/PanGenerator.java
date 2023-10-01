package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.controller.RequestDto;
import com.devmeks.pangenerator.model.PAN;
import com.devmeks.pangenerator.repository.PANRepo;
import com.devmeks.pangenerator.utility.PanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public final class PanGenerator {
    private final PanUtils panUtils;
    private final PANRepo panRepo;


    @Autowired
    public PanGenerator(PANRepo panRepo, PanUtils panUtils) {
        this.panRepo = panRepo;
        this.panUtils = panUtils;
    }






    public Mono<String> createPanFromMobileNumber(RequestDto requestDto){


        StringBuilder panBuilder = new StringBuilder();
        String iin = panUtils.retrieveIin(requestDto.getCardScheme());



        String partialPan = iin + requestDto.getMobileNumber().substring(2);

        String pan = panBuilder
                .append(partialPan)
                .append(panUtils.generateChecksumDigit(partialPan)).toString();

        PAN panObject = PAN.builder()
                        .cardNumber(pan)
                        .build();

        PAN returnedPANObject;


        try{
            returnedPANObject = panRepo.save(panObject);

        }catch(Exception e){
            return processException(e, requestDto);
        }

        return Mono.just(returnedPANObject.getCardNumber());


    }



    public  Mono<String> generateRandomPan(RequestDto requestDto){

        StringBuilder panBuilder = new StringBuilder();

        String iin = panUtils.retrieveIin(requestDto.getCardScheme());

        String partialPan = iin + panUtils.generateRandomDigits().substring(1);

        String pan = panBuilder
                .append(partialPan)
                .append(panUtils.generateChecksumDigit(partialPan)).toString();

        PAN panObject = PAN.builder()
                .cardNumber(pan)
                .build();


        PAN returnedPANObject = panRepo.save(panObject);
        log.info("Random PAN is {}", returnedPANObject.getCardNumber());

        return Mono.just(returnedPANObject.getCardNumber());

    }



    private Mono<String> processException(Exception e, RequestDto requestDto){
        log.error(e.getMessage());
        String exceptionType = e.getClass().toString();
        int lastDotIndex = exceptionType.lastIndexOf('.');

        switch (exceptionType.substring(lastDotIndex + 1)){
            case "DataIntegrityViolationException":
                log.info("Generating random {} PAN............", requestDto.getCardScheme());
                return generateRandomPan(requestDto);

            default:
                return Mono.just("An Error occurred");

        }

    }
}
