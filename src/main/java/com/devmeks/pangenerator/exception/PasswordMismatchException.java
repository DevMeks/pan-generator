package com.devmeks.pangenerator.exception;

public class PasswordMismatchException extends Exception{
  private static final String ERROR_MESSAGE = "Passwords provided do not match";

  public PasswordMismatchException() {
    super(ERROR_MESSAGE);
  }
}
