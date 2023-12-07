package com.devmeks.pangenerator.util;


import com.devmeks.pangenerator.config.BinProperties;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Objects;


/**
 * The type Pan utils.
 */
@Component
@Slf4j
public class PanUtils {


  private static final SecureRandom RANDOM = new SecureRandom();


  private final BinProperties binProperties;


  /**
   * Instantiates a new Pan utils.
   *
   * @param binProperties the bin properties
   */
  @Autowired
  public PanUtils(BinProperties binProperties) {
    this.binProperties = binProperties;
  }


  /**
   * Retrieves the BIN from the property file based on the card
   * scheme provided
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
   * This method generates random digits of length 10. It generates it in two parts of 5
   * to further increase the chance of a random Pan being generated
   *
   * @return the string
   */

  public String generateRandomNumbers(int numRandomNumbers) {
    if (numRandomNumbers <= 0) {
      numRandomNumbers = 10;
    }


    StringBuilder randomNumbersString = new StringBuilder();

    for (int i = 0; i < numRandomNumbers; i++) {
      int randomNumber = RANDOM.nextInt(100); // Generates a random number between 0 and 99
      randomNumbersString.append(randomNumber);
    }



    return randomNumbersString.substring(0,numRandomNumbers);
  }

  /**
   * Generate Luhn check digit string.
   *
   * @param partialCardNumber the partial card number
   * @return the string
   */


  public  String generateLuhnCheckDigit(String partialCardNumber) {
    if (partialCardNumber == null)
      return null;
    String digit;
    /* convert to array of int for simplicity */
    int[] digits = new int[partialCardNumber.length()];
    for (int i = 0; i < partialCardNumber.length(); i++) {
      digits[i] = Character.getNumericValue(partialCardNumber.charAt(i));
    }

    /* double every other starting from right - jumping from 2 in 2 */
    for (int i = digits.length - 1; i >= 0; i -= 2)    {
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

    /* convert to string to be easier to take the last digit */
    digit = sum + "";
    return digit.substring(digit.length() - 1);
  }



  /**
   * Is valid mobile number boolean.
   *
   * @param mobileNumber the mobile number
   * @return the boolean
   */
  public boolean isValidMobileNumber(String mobileNumber) {

    return mobileNumber.length() == 11;

  }


  /**
   * This method processes the response from the Pan creation process
   *
   * @param monoResponse the mono response
   * @return the response entity
   */
  public ResponseEntity<Mono<ResponseDto>> processResponse(Mono<ResponseDto> monoResponse) {

    if (Objects.requireNonNull(monoResponse.block())
        .getResponseStatus()
        .equals(ResponseStatus.SUCCESSFUL)) {

      return ResponseEntity.status(HttpStatus.OK)
          .body(monoResponse);

    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(monoResponse);

  }


}
