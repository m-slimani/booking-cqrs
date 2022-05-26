package com.cola.booking.command.domain;

public class FunctionalException extends Exception {

  private String message;

  public FunctionalException(String message) {
    super(message);
  }
}
