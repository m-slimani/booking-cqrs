package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import com.cola.booking.command.domain.event.BookingEvent;
import com.cola.booking.command.domain.FunctionalException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface BookingStore {

  Booking findById(Long bookingId) throws NotFoundException;

  Booking save(Booking booking);

  void sendNotificationEvent(BookingEvent bookingEvent);

  void cancel(Booking booking) throws FunctionalException;
}
