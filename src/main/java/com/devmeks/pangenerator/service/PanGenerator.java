package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.model.request.CreatePANFromMobileNumDto;
import com.devmeks.pangenerator.model.PAN;
import com.devmeks.pangenerator.model.response.APIError;
import com.devmeks.pangenerator.model.response.ResponseDto;
import com.devmeks.pangenerator.repository.PANRepo;
import com.devmeks.pangenerator.utility.PanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class PanGenerator {
    private final PanUtils panUtils;
    private final PANRepo panRepo;


    @Autowired
    public PanGenerator(PANRepo panRepo, PanUtils panUtils) {
        this.panRepo = panRepo;
        this.panUtils = panUtils;
    }





    /** This method was created to generate PAN from a Nigerian mobile
     * Number. The mobile has to be 11 digits in length*/
    public Mono<ResponseDto> createPanFromMobileNumber(CreatePANFromMobileNumDto requestDto){

        ResponseDto responseDto = new ResponseDto();
        var apiError = APIError.ceateAPIError();

        //validate mobile number
        if(panUtils.isValidMobileNumber(requestDto.getMobileNumber())){
            StringBuilder panBuilder = new StringBuilder();
            String iin;
            PAN returnedPANObject;

            try{
                iin = panUtils.retrieveIin(requestDto.getCardScheme());

                String partialPan = iin + requestDto.getMobileNumber().substring(2);

                String pan = panBuilder
                        .append(partialPan)
                        .append(panUtils.generateChecksumDigit(partialPan)).toString();

                PAN panObject = PAN.builder()
                        .cardNumber(pan)
                        .build();



                returnedPANObject = panRepo.save(panObject);

            }catch(Exception e){
                return processException(e, requestDto);
            }

            responseDto.setPan(returnedPANObject.getCardNumber());

            return Mono.just(responseDto);

        }

        log.error("Invalid mobile Number provided........{}", requestDto.getMobileNumber());

        apiError.setErrorMessage("Mobile Number is not 11 digits long");
        responseDto.setError(apiError);
        return Mono.just(responseDto);


    }



    public  Mono<ResponseDto> generateRandomPan(CreatePANFromMobileNumDto requestDto){

        StringBuilder panBuilder = new StringBuilder();
        ResponseDto responseDto = new ResponseDto();

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

        responseDto.setPan(returnedPANObject.getCardNumber());

        return Mono.just(responseDto);

    }



    private Mono<ResponseDto> processException(Exception e, CreatePANFromMobileNumDto requestDto){

        var apiError = APIError.ceateAPIError();

        log.error("Error Details:........{} exception error",e.getMessage());
        String exceptionType = e.getClass().getSimpleName();
        ResponseDto responseDto = new ResponseDto();

        switch (exceptionType){
            case "DataIntegrityViolationException":
                log.info("Generating random {} PAN............", requestDto.getCardScheme());

                return generateRandomPan(requestDto);

            case "NullPointerException":
                if(Objects.isNull(requestDto.getCardScheme())){
                    log.error("No value has been passed for cardScheme parameter");
                    apiError.setErrorMessage("Empty cardScheme parameter");
                    responseDto.setError(apiError);
                    return Mono.just(responseDto);
                }

                log.error("No value passed for mobileNumber parameter");
                apiError.setErrorMessage("Empty mobileNumber parameter");
                responseDto.setError(apiError);

                return Mono.just(responseDto);

            default:
                apiError.setErrorMessage("Empty mobileNumber parameter");
                responseDto.setError(apiError);
                return Mono.just(responseDto);

        }

    }
}
