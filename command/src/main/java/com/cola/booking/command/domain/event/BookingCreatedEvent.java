package com.cola.booking.command.domain.event;

import com.cola.booking.command.domain.Booking;

public class BookingCreatedEvent extends BookingEvent {

  public BookingCreatedEvent(Booking booking) {
    super(booking);
  }

}
