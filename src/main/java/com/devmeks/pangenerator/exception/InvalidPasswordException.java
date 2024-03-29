package com.devmeks.pangenerator.exception;

public class InvalidPasswordException extends Exception{
  private static final String ERROR_MESSAGE = "Invalid password provided";

  public InvalidPasswordException() {
    super(ERROR_MESSAGE);
  }
}
