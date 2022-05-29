package com.cola.booking.query.domain;

public enum AvailabilityStatusEnum {
  FREE("free"),
  BOOKED("booked");

  private String value;

  AvailabilityStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
