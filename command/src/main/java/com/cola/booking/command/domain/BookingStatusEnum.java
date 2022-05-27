package com.cola.booking.command.domain;

public enum BookingStatusEnum {

  BOOKED_STATUS("booked");

  private String value;

  BookingStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
