package com.cola.booking.query.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BookingHistory {

  private Long id;
  private Long userId;
  private String participants;
  private String roomNumber;
  private Long slotNumber;
  private String status;

}
