package com.cola.booking.query.application.kafka;

public enum EventTypeEnum {

  CREATE("create"),
  CANCEL("cancel");

  private String value;

  EventTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
