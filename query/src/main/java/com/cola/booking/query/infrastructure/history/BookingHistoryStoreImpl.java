package com.cola.booking.query.infrastructure.history;

import com.cola.booking.query.domain.BookingHistory;
import java.util.List;
import java.util.Optional;
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
        .map(BookingHistoryEntityMapper.BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<BookingHistory> getHistory(Integer userId, String status) {
    return bookingHistoryRepository.findByUserIdAndStatus(Long.valueOf(userId), status)
        .stream()
        .map(BookingHistoryEntityMapper.BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<BookingHistory> FindBookingById(Integer bookingId) {
    return bookingHistoryRepository.findById(Long.valueOf(bookingId)).stream()
        .map(BookingHistoryEntityMapper.BOOKING_HISTORY_ENTITY_MAPPER::fromEntity)
        .findFirst();
  }
}
