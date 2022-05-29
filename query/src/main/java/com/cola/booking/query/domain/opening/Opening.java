package com.cola.booking.query.domain.opening;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Opening {

  private final String roomNumber;
  private final LocalDateTime startDateTime;

  public Opening(String roomNumber, LocalDateTime startDateTime) {
    this.roomNumber = roomNumber;
    this.startDateTime = startDateTime;
  }
}
