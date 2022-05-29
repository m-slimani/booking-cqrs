package com.cola.booking.query.infrastructure.history;

import static com.cola.booking.query.infrastructure.history.BookingHistoryEntityMapper.BOOKING_HISTORY_ENTITY_MAPPER;

import com.cola.booking.query.domain.BookingHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingHistoryStoreImpl implements BookingHistoryStore {

  private BookingHistoryRepository bookingHistoryRepository;

  @Override
  public List<BookingHistory> getHistory(Integer userId) {
    return bookingHistoryRepository.findByUserId(Long.valueOf(userId)).stream()
        .map(BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<BookingHistory> getHistory(Integer userId, String status) {
    return bookingHistoryRepository.findByUserIdAndStatus(Long.valueOf(userId), status).stream()
        .map(BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<BookingHistory> findByStartDateTimeBetweenAndStatus(LocalDate date, String status) {
    LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
    LocalDateTime end = LocalDateTime.of(date, LocalTime.of(23, 59, 59));
    return bookingHistoryRepository.findByStartDateTimeBetweenAndStatus(start, end, status).stream()
        .map(BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }
}
