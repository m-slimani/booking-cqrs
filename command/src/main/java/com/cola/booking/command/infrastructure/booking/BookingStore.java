package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import com.cola.booking.command.domain.FunctionalException;
import com.cola.booking.command.domain.event.BookingEvent;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface BookingStore {

  Booking findById(Long bookingId) throws NotFoundException;

  List<Booking> findBookings(String roomNumber, LocalDateTime startDateTime);

  Booking save(Booking booking);

  void sendNotificationEvent(BookingEvent bookingEvent);

  void cancel(Booking booking) throws FunctionalException;
}
