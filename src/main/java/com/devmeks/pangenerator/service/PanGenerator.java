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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;


/**
 * The type Pan generator.
 */
@Service
@Slf4j
public class PanGenerator {
  private static final int PARTIAL_LENGTH_OF_LOCAL_VERVE_CARD = 18;
  private static final int PARTIAL_LENGTH_OF_CARD = 15;
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
   * Create pan from mobile number mono.
   *
   * @param requestDto the request dto
   * @return the mono
   */
  public Mono<ResponseDto> createPanFromMobileNumber(@Valid CreatePanFromMobileNumDto requestDto) {
    ResponseDto responseDto = new ResponseDto();
    StringBuilder panBuilder = new StringBuilder();
    String bankBin;
    Pan returnedPanObject;
    String partialPan;

    try {
      bankBin = panUtils.retrieveIin(requestDto.getCardScheme());
      partialPan = bankBin + requestDto.getMobileNumber().substring(2);

      //caters for 19 digit PAN length for verve card scheme
      if (requestDto.getCardScheme().equalsIgnoreCase("verve") &&
          !(requestDto.isGlobalVerveCard())
      ) {
        partialPan = getPartialCardNumber(partialPan, partialPan.length(), PARTIAL_LENGTH_OF_LOCAL_VERVE_CARD);
      }
      //caters for 16 digit PAN length
      else {
        partialPan = getPartialCardNumber(partialPan, partialPan.length(), PARTIAL_LENGTH_OF_CARD);

      }


      String pan = panBuilder
          .append(partialPan)
          .append(panUtils.generateLuhnCheckDigit(partialPan)).toString();


      Pan panObject = Pan.builder()
          .cardNumber(pan)
          .build();


      returnedPanObject = panRepo.save(panObject);

    } catch (Exception e) {
      return processException(e, requestDto);
    }

    responseDto.setPan(returnedPanObject.getCardNumber());
    responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);

    log.info("RESPONSE TO CALLER IS:: {}", responseDto);

    return Mono.just(responseDto);


  }

  private String getPartialCardNumber(String panWithoutCheckDigit, int partialPanLength, int maxPartialPanLength) {
    int requiredRandomNumbers = maxPartialPanLength - partialPanLength;


    if (partialPanLength > maxPartialPanLength) {
      panWithoutCheckDigit = panWithoutCheckDigit.substring(0, maxPartialPanLength);
    } else if (panWithoutCheckDigit.length() < maxPartialPanLength) {
      panWithoutCheckDigit = panWithoutCheckDigit
          + panUtils.generateRandomNumbers(requiredRandomNumbers);

    }

    return panWithoutCheckDigit;


  }


  /**
   * Generate random pan mono.
   *
   * @param cardScheme    the card scheme
   * @param isGlobalVerve the is global verve
   * @return the mono
   */
  public Mono<ResponseDto> generateRandomPan(String cardScheme, boolean isGlobalVerve) {

    StringBuilder panBuilder = new StringBuilder();
    ResponseDto responseDto = new ResponseDto();
    String partialPan;
    int requiredRandomDigits;

    String cardBin = panUtils.retrieveIin(cardScheme);

    //cater for 19 digit PAN when card scheme is Verve
    if (cardScheme.equalsIgnoreCase("verve") &&
        !(isGlobalVerve)) {

      requiredRandomDigits = 18 - cardBin.length();

    } else {
      requiredRandomDigits = 15 - cardBin.length();

    }
    partialPan = cardBin + panUtils.generateRandomNumbers(requiredRandomDigits);


    String pan = panBuilder
        .append(partialPan)
        .append(panUtils.generateLuhnCheckDigit(partialPan)).toString();

    Pan panObject = Pan.builder()
        .cardNumber(pan)
        .build();


    Pan returnedPanObject = panRepo.save(panObject);
    log.info("Random Pan is {}", returnedPanObject.getCardNumber());

    responseDto.setPan(returnedPanObject.getCardNumber());
    responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);
    log.info("RESPONSE TO CALLER IS:: {}", responseDto);

    return Mono.just(responseDto);

  }

  public  Mono<ResponseDto> getPans(int pageNumber, int pageSize){
    ResponseDto responseDto = new ResponseDto();
    List<Pan> listOfPans;



    try{
      if(pageNumber == 0 || pageSize == 0){
        listOfPans = panRepo.findAll();

      }else{
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        listOfPans =  panRepo.findAll(pageable).toList();

      }

      responseDto = ResponseDto.builder()
          .pans(listOfPans)
          .responseStatus(ResponseStatus.SUCCESSFUL)
          .build();


    }catch(Exception e){
      log.error("An error occurred while trying to retrieve pans from the db:{}", e.getMessage());


    }



    return Mono.just(responseDto);
  }


  private Mono<ResponseDto> processException(Exception e, CreatePanFromMobileNumDto requestDto) {

    var apiError = ApiError.ceateApiError();

    log.error("Error Details:........{} exception error", e.getMessage());
    String exceptionType = e.getClass().getSimpleName();
    ResponseDto responseDto = new ResponseDto();

    //if pan exists generate new random pan
    if (exceptionType.equals("DataIntegrityViolationException")) {
      log.info("Generating random {} Pan............", requestDto.getCardScheme());

      return generateRandomPan(requestDto.getCardScheme(), requestDto.isGlobalVerveCard());
    }
    apiError.setErrorMessage("Empty mobileNumber parameter");
    responseDto.setError(apiError);
    return Mono.just(responseDto);

  }
}
