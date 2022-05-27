package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import com.cola.booking.command.domain.event.BookingEvent;
import com.cola.booking.command.domain.FunctionalException;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface BookingStore {

  Booking findById(Long bookingId) throws NotFoundException;

  List<Booking> findByRoomNumberAndSlotNumber(String roomNumber, Long slotNumber);

  Booking save(Booking booking);

  void sendNotificationEvent(BookingEvent bookingEvent);

  void cancel(Booking booking) throws FunctionalException;
}
