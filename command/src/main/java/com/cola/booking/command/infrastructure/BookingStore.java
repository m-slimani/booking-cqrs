package com.cola.booking.command.infrastructure;

import com.cola.booking.command.domain.Booking;

public interface BookingStore {

  Booking save(Booking booking);

  void cancel(Booking booking);

}
