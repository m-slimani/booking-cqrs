package com.cola.booking.query.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BookingHistory {

  private Long id;
  private Long userId;
  private String roomNumber;
  private LocalDateTime startDateTime;
  private String participants;
  private String status;
}
