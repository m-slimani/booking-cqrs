package com.cola.booking.query.domain;

import com.cola.booking.query.infrastructure.history.BookingHistoryStore;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingHistoryService {

  private BookingHistoryStore bookingHistoryStore;

  public List<BookingHistory> getHistory(Integer userId, String status) {
    return Objects.isNull(status)
        ? bookingHistoryStore.getHistory(userId)
        : bookingHistoryStore.getHistory(userId, status);
  }

  public void save(BookingHistory bookingHistory) {
        bookingHistoryStore.saveHistory(bookingHistory);
  }
}
