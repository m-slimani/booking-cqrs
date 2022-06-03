package com.cola.booking.command.domain.event;

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
