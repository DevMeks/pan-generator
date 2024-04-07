package com.devmeks.pangenerator.exception;

public class ExpiredTokenException extends Exception{
  private static final String ERROR_MESSAGE = "Token has expired";

  public ExpiredTokenException() {
    super(ERROR_MESSAGE);
  }
}
