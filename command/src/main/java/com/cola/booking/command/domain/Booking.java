package com.cola.booking.command.domain;

import java.util.List;
import java.util.Objects;

public class Booking {

  private final Long id;
  private final Long organiserNumber;
  private final String roomNumber;
  private final Long slotNumber;
  private final List<String> participants;
  private final String status;

  public Booking(Long id, Long organiserNumber, String roomNumber, Long slotNumber, List<String> participants, String status) {
    this.id = id;
    this.organiserNumber = organiserNumber;
    this.roomNumber = roomNumber;
    this.slotNumber = slotNumber;
    this.participants = participants;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public Long getOrganiserNumber() {
    return organiserNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public Long getSlotNumber() {
    return slotNumber;
  }

  public List<String> getParticipants() {
    return participants;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Booking booking = (Booking) o;
    return Objects.equals(id, booking.id) &&
        Objects.equals(organiserNumber, booking.organiserNumber) &&
        Objects.equals(roomNumber, booking.roomNumber) &&
        Objects.equals(slotNumber, booking.slotNumber) &&
        Objects.equals(participants, booking.participants) &&
        Objects.equals(status, booking.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, organiserNumber, roomNumber, slotNumber, participants, status);
  }

  @Override
  public String toString() {
    return "Booking{" +
        "id=" + id +
        ", organiserNumber=" + organiserNumber +
        ", roomNumber='" + roomNumber + '\'' +
        ", slotNumber=" + slotNumber +
        ", participants=" + participants +
        ", status='" + status + '\'' +
        '}';
  }
}
