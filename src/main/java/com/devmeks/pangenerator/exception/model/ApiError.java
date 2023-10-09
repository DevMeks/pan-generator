package com.devmeks.pangenerator.exception.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The type Api error.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApiError {
  private String errorMessage;


  /**
   * Ceate api error api error.
   * This is a factory method
   *
   * @return the api error
   */
  public static ApiError ceateApiError() {
    return new ApiError();
  }
}
