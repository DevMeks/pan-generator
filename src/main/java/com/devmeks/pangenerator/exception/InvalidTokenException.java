package com.devmeks.pangenerator.exception;

public class InvalidTokenException extends Exception{
  private static final String ERROR_MESSAGE = "Invalid Token Provided";

  public InvalidTokenException() {
    super(ERROR_MESSAGE);
  }
}
