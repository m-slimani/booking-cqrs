package com.cola.booking.command.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Booking {

  private final Long id;
  private final Long userId;
  private final String roomNumber;
  private final LocalDateTime startDateTime;
  private final String status;
  private final List<String> participants;

}
