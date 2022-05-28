package com.cola.booking.query.infrastructure.history;

import com.cola.booking.query.domain.BookingHistory;
import java.util.List;
import java.util.Optional;

public interface BookingHistoryStore {

  List<BookingHistory> getHistory(Integer organiserNumber);

  List<BookingHistory> getHistory(Integer organiserNumber, String status);

  Optional<BookingHistory> FindBookingById(Integer bookingId);
}
