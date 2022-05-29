package com.cola.booking.query.infrastructure.history;

import com.cola.booking.query.domain.BookingHistory;
import java.time.LocalDate;
import java.util.List;

public interface BookingHistoryStore {

  List<BookingHistory> getHistory(Integer organiserNumber);

  List<BookingHistory> getHistory(Integer organiserNumber, String status);

  List<BookingHistory> findByStartDateTimeBetweenAndStatus(LocalDate date, String status);

  void saveHistory(BookingHistory bookingHistory);
}
