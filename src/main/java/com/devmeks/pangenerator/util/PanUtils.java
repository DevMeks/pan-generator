package com.devmeks.pangenerator.util;


import com.devmeks.pangenerator.config.BinProperties;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import java.security.SecureRandom;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


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
  public  String generateLuhnCheckDigit(String partialCardNumber) {
    if (partialCardNumber == null) {
      return null;

    }

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

    if (Objects.requireNonNull(monoResponse.block())
        .getResponseStatus()
        .equals(ResponseStatus.SUCCESSFUL)) {

      return ResponseEntity.status(HttpStatus.OK)
          .body(monoResponse);

    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(monoResponse);

  }

  public String generateTransactionId() {
    StringBuilder generatedTransactionId = new StringBuilder();

    for (int i = 0; i < 14; i++) {
      generatedTransactionId.append(RANDOM.nextInt(9));
    }


    return generatedTransactionId.toString();
  }




}
