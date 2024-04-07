package com.devmeks.pangenerator.exception;

public class TokenExpiredException extends Exception{
  private static final String ERROR_MESSAGE = "Token has expired";

  public TokenExpiredException() {
    super(ERROR_MESSAGE);
  }
}
