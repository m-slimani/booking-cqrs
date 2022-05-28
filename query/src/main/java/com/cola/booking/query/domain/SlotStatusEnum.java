package com.cola.booking.query.domain;

public enum SlotStatusEnum {
  FREE("free"),
  BOOKED("booked");

  private String value;

  SlotStatusEnum(String value) {
    this.value=value;
  }

  public String getValue() {
    return value;
  }
}
