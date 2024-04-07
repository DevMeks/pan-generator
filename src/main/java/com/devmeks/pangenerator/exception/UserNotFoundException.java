package com.devmeks.pangenerator.exception;

public class UserNotFoundException extends Exception{
  private static final String ERROR_MESSAGE = "User does not exist";

  public UserNotFoundException() {
    super(ERROR_MESSAGE);
  }
}
