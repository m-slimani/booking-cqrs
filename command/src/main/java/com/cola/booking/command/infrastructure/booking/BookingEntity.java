package com.cola.booking.command.infrastructure.booking;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long organiserNumber;
  private String participants;
  private String roomNumber;
  private Long slotNumber;
  private String status;

  public Long getId() {
    return id;
  }

  public Long getOrganiserNumber() {
    return organiserNumber;
  }

  public String getParticipants() {
    return participants;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public Long getSlotNumber() {
    return slotNumber;
  }

  public String getStatus() {
    return status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setOrganiserNumber(Long organiserNumber) {
    this.organiserNumber = organiserNumber;
  }

  public void setParticipants(String participants) {
    this.participants = participants;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public void setSlotNumber(Long slotNumber) {
    this.slotNumber = slotNumber;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
