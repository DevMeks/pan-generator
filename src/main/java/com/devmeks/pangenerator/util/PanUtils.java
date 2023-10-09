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

import java.util.Objects;
import java.util.Random;


/**
 * The type Pan utils.
 */
@Component
@Slf4j
public class PanUtils {

  private static final Random RANDOM = new Random(System.currentTimeMillis());
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

    switch (cardScheme.toLowerCase()) {
      case "verve":
        return binProperties.getVerve();
      case "mastercard":
        return binProperties.getMastercard();
      default:
        return "000000";

    }


  }

  /**
   * This method generates random digits of length 10. It generates it in two parts of 5
   * to further increase the chance of a random Pan being generated
   *
   * @return the string
   */
  public String generateRandomDigits() {

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 2; i++) {
      long m = (long) Math.pow(10, 4);
      long partialDigits = m + RANDOM.nextInt((int) (9 * m));
      builder.append(partialDigits);
    }


    return builder.toString();


  }


  /**
   * This method generates the checksum digit of a Pan
   *
   * @param partialCardNumber the partial card number
   * @return the string
   */
  public String generateChecksumDigit(String partialCardNumber) {
    int sum = 0;
    for (int i = 0; i < partialCardNumber.length(); i++) {

      // Get the digit at the current position.
      int digit = Integer.parseInt(partialCardNumber.substring(i, (i + 1)));

      if ((i % 2) == 0) {
        digit = digit * 2;
        if (digit > 9) {
          digit = (digit / 10) + (digit % 10);
        }
      }
      sum += digit;
    }

    // The check digit is the number required to make the sum a multiple of 10.
    int mod = sum % 10;
    return String.valueOf((mod == 0) ? 0 : 10 - mod);
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
