package com.cola.booking.query.infrastructure.history;

import static com.cola.booking.query.domain.AvailabilityStatusEnum.BOOKED;
import static com.cola.booking.query.infrastructure.history.BookingHistoryEntityMapper.BOOKING_HISTORY_ENTITY_MAPPER;

import com.cola.booking.query.domain.BookingHistory;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookingHistoryEntityMapperTest {

  private static final LocalDateTime JANUARY_FIRST_EIGHT_AM = LocalDateTime.of(2022, 1, 1, 8, 0, 0);

  @Test
  @DisplayName("1) null value should return null")
  void nullValue() {
    Assertions.assertThat(BOOKING_HISTORY_ENTITY_MAPPER.fromEntity(null)).isNull();
  }

  @Test
  @DisplayName("2) entity value should be mapped")
  void fromEntity() {
    BookingHistoryEntity bookingHistoryEntity = buildEntity();
    BookingHistory expected =
        BookingHistory.builder()
            .id(1L)
            .roomNumber("C01")
            .userId(1L)
            .startDateTime(JANUARY_FIRST_EIGHT_AM)
            .status(BOOKED.getValue())
            .participants("Eric,Marc")
            .build();
    Assertions.assertThat(BOOKING_HISTORY_ENTITY_MAPPER.fromEntity(bookingHistoryEntity))
        .isEqualTo(expected);
  }

  private BookingHistoryEntity buildEntity() {
    return BookingHistoryEntity.builder()
        .id(1L)
        .roomNumber("C01")
        .userId(1L)
        .startDateTime(JANUARY_FIRST_EIGHT_AM)
        .status(BOOKED.getValue())
        .participants("Eric,Marc")
        .build();
  }
}
