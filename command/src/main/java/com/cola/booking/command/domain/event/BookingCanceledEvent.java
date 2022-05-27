package com.cola.booking.command.domain.event;

import com.cola.booking.command.domain.Booking;

public class BookingCanceledEvent extends BookingEvent {

  public BookingCanceledEvent(Booking booking) {
    super(booking);
  }

}
