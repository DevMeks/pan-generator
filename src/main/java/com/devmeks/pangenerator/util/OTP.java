package com.devmeks.pangenerator.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
public class OTP {

  private final String code;
  private final LocalDateTime expiryDateTime;


  public boolean isValid(){

    return LocalDateTime.now().isBefore(expiryDateTime);
  }





}
