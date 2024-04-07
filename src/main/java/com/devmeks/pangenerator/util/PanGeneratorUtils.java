package com.devmeks.pangenerator.util;


import com.devmeks.pangenerator.config.BinProperties;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.ApiError;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * The type Pan utils.
 */
@Component
@Slf4j
public final class PanGeneratorUtils {

  private static final SecureRandom RANDOM = new SecureRandom();
  private static final long OTP_VALIDITY_MINUTES = 5;

  private final BCryptPasswordEncoder passwordEncoder;


  private final BinProperties binProperties;


  /**
   * Instantiates a new Pan utils.
   *
   * @param binProperties the bin properties
   */
  @Autowired
  public PanGeneratorUtils(BinProperties binProperties, BCryptPasswordEncoder passwordEncoder) {
    this.binProperties = binProperties;
    this.passwordEncoder = passwordEncoder;
  }


  /**
   * Retrieve iin string.
   *
   * @param cardScheme the card scheme
   * @return the string
   */
  public String retrieveIin(String cardScheme) {

    return switch (cardScheme.toLowerCase()) {
      case "verve" -> binProperties.getVerve();
      case "mastercard" -> binProperties.getMastercard();
      default -> "000000";
    };


  }

  /**
   * Generate random numbers string.
   *
   * @param numberOfRequiredRandomNumbers the number of required random numbers
   * @return the string
   */
  public String generateRandomNumbers(int numberOfRequiredRandomNumbers) {
    if (numberOfRequiredRandomNumbers <= 0) {
      numberOfRequiredRandomNumbers = 10;
    }


    StringBuilder randomNumbersString = new StringBuilder();

    for (int i = 0; i < numberOfRequiredRandomNumbers; i++) {
      int randomNumber = RANDOM.nextInt(100); // Generates a random number between 0 and 99
      randomNumbersString.append(randomNumber);
    }


    return randomNumbersString.substring(0, numberOfRequiredRandomNumbers);
  }


  /**
   * Generate Luhn check digit string.
   *
   * @param partialCardNumber the partial card number
   * @return the string
   */
  public String generateLuhnCheckDigit(String partialCardNumber) {
    if (partialCardNumber == null) {
      return null;

    }

    /* convert to array of int for simplicity */
    int[] digits = new int[partialCardNumber.length()];
    for (int i = 0; i < partialCardNumber.length(); i++) {
      digits[i] = Character.getNumericValue(partialCardNumber.charAt(i));
    }

    /* double every other starting from right - jumping from 2 in 2 */
    for (int i = digits.length - 1; i >= 0; i -= 2) {
      digits[i] += digits[i];
      /* taking the sum of digits grater than 10 - simple trick by subtract 9 */
      if (digits[i] >= 10) {
        digits[i] = digits[i] - 9;
      }
    }
    int sum = 0;
    for (int j : digits) {
      sum += j;
    }
    /* multiply by 9 step */
    sum = sum * 9;

    String digit;

    /* convert to string to be easier to take the last digit */
    digit = sum + "";
    return digit.substring(digit.length() - 1);
  }


  /**
   * Is mobile number valid boolean.
   *
   * @param mobileNumber the mobile number
   * @return the boolean
   */
  public boolean isMobileNumberValid(String mobileNumber) {

    return mobileNumber.length() == 11;

  }


  /**
   * Process response response entity.
   *
   * @param monoResponse the mono response
   * @return the response entity
   */
  public ResponseEntity<Mono<ResponseDto>> processResponse(Mono<ResponseDto> monoResponse) {

    HttpStatus status = HttpStatus.NO_CONTENT;

    switch (Objects.requireNonNull(monoResponse.block())
        .getResponseStatus()) {

      case SUCCESSFUL -> status = HttpStatus.OK;

      case INVALID_REQUEST -> status = HttpStatus.BAD_REQUEST;


      case NO_RECORD_FOUND -> status = HttpStatus.NOT_FOUND;


    }

    return ResponseEntity.status(status).body(monoResponse);


  }

  public String generateTransactionId() {
    StringBuilder generatedTransactionId = new StringBuilder();

    for (int i = 0; i < 14; i++) {
      generatedTransactionId.append(RANDOM.nextInt(9));
    }


    return generatedTransactionId.toString();
  }


  public String encryptPassword(String clearPassword) {
    return passwordEncoder.encode(clearPassword);
  }

  public boolean isPasswordValid(String rawPassword, String encryptedPassword) {

    return passwordEncoder.matches(rawPassword, encryptedPassword);
  }

  public Mono<ResponseDto> processException(Exception e) {

    var apiError = ApiError.ceateApiError();

    log.error("{}: AN ERROR OCCURRED - DETAIL - {}",e.getClass().getSimpleName(), e.getMessage());
    ResponseDto responseDto = new ResponseDto();

    apiError.setErrorMessage(e.getLocalizedMessage());
    responseDto.setError(apiError);
    responseDto.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    return Mono.just(responseDto);

  }


  public Mono<ResponseDto> processException(Exception e, String errorMessage) {

    var apiError = ApiError.ceateApiError();

    log.error("{}: AN ERROR OCCURRED - DETAIL - {}",e.getClass().getSimpleName(), e.getMessage());
    ResponseDto responseDto = new ResponseDto();

    apiError.setErrorMessage(errorMessage);
    responseDto.setError(apiError);
    responseDto.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    return Mono.just(responseDto);

  }


  public static OTP generateOTP(int length){
    String otp  = RandomStringUtils.randomNumeric(length);
    LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);
    return new OTP(otp,expiryDateTime);
  }


}
