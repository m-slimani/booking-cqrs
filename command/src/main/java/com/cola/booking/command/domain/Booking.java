package com.cola.booking.command.domain;

import java.util.List;

public class Booking {

  private Long bookingId;
  private Integer organiserNumber;
  private String roomNumber;
  private Long slotNumber;
  private List<String> participants;


  public Booking(Long bookingId, Integer organiserNumber, String roomNumber, Long slotNumber,
      List<String> participants) {
    this.bookingId = bookingId;
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

  public Long getSlotNumber() {
    return slotNumber;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public List<String> getParticipants() {
    return participants;
  }
}
