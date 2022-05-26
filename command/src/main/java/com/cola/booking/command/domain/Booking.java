package com.cola.booking.command.domain;

import java.util.List;

public class Booking {

  private Integer organiserNumber;
  private String roomNumber;
  private Integer slotNumber;
  private List<String> participants;

  public Booking(Integer organiserNumber, String roomNumber, Integer slotNumber, List<String> participants) {
    this.organiserNumber = organiserNumber;
    this.roomNumber = roomNumber;
    this.slotNumber = slotNumber;
    this.participants = participants;
  }

  public Integer getOrganiserNumber() {
    return organiserNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public Integer getSlotNumber() {
    return slotNumber;
  }
}
