package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.model.ApiError;
import com.devmeks.pangenerator.model.Pan;
import com.devmeks.pangenerator.repository.PanRepo;
import com.devmeks.pangenerator.util.PanUtils;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * The type Pan generator.
 */
@Service
@Slf4j
public class PanGenerator {
  private final PanUtils panUtils;
  private final PanRepo panRepo;


  /**
   * Instantiates a new Pan generator.
   *
   * @param panRepo  the pan repo
   * @param panUtils the pan utils
   */
  @Autowired
  public PanGenerator(PanRepo panRepo, PanUtils panUtils) {
    this.panRepo = panRepo;
    this.panUtils = panUtils;
  }


  /**
   * This method was created to generate Pan from a Nigerian mobile
   * Number. The mobile has to be 11 digits in length @param requestDto the request dto
   *
   * @return the mono
   */
  public Mono<ResponseDto> createPanFromMobileNumber(@Valid CreatePanFromMobileNumDto requestDto) {
    ResponseDto responseDto = new ResponseDto();


    StringBuilder panBuilder = new StringBuilder();
    String iin;
    Pan returnedPanObject;

    try {
      iin = panUtils.retrieveIin(requestDto.getCardScheme());

      String partialPan = iin + requestDto.getMobileNumber().substring(2);

      @LuhnCheck String pan = panBuilder
          .append(partialPan)
          .append(panUtils.generateChecksumDigit(partialPan)).toString();


      Pan panObject = Pan.builder()
          .cardNumber(pan)
          .build();


      returnedPanObject = panRepo.save(panObject);

    } catch (Exception e) {
      return processException(e, requestDto);
    }

    responseDto.setPan(returnedPanObject.getCardNumber());
    responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);

    return Mono.just(responseDto);


  }


  /**
   * Generate random pan mono.
   *
   * @param requestDto the request dto
   * @return the mono
   */
  public Mono<ResponseDto> generateRandomPan(@Valid CreatePanFromMobileNumDto requestDto) {

    StringBuilder panBuilder = new StringBuilder();
    ResponseDto responseDto = new ResponseDto();

    String iin = panUtils.retrieveIin(requestDto.getCardScheme());

    String partialPan = iin + panUtils.generateRandomDigits().substring(1);

    String pan = panBuilder
        .append(partialPan)
        .append(panUtils.generateChecksumDigit(partialPan)).toString();

    Pan panObject = Pan.builder()
        .cardNumber(pan)
        .build();


    Pan returnedPanObject = panRepo.save(panObject);
    log.info("Random Pan is {}", returnedPanObject.getCardNumber());

    responseDto.setPan(returnedPanObject.getCardNumber());
    responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);

    return Mono.just(responseDto);

  }


  private Mono<ResponseDto> processException(Exception e, CreatePanFromMobileNumDto requestDto) {

    var apiError = ApiError.ceateApiError();

    log.error("Error Details:........{} exception error", e.getMessage());
    String exceptionType = e.getClass().getSimpleName();
    ResponseDto responseDto = new ResponseDto();

    if (exceptionType.equals("DataIntegrityViolationException")) {
      log.info("Generating random {} Pan............", requestDto.getCardScheme());

      return generateRandomPan(requestDto);
    }
    apiError.setErrorMessage("Empty mobileNumber parameter");
    responseDto.setError(apiError);
    return Mono.just(responseDto);

  }
}
