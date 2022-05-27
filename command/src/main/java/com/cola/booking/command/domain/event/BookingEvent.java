package com.cola.booking.command.domain.event;

import com.cola.booking.command.domain.Booking;
import java.util.Objects;

public class BookingEvent {

  private Booking booking;

  public BookingEvent(Booking booking) {
    this.booking = booking;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookingEvent that = (BookingEvent) o;
    return Objects.equals(booking, that.booking);
  }

  @Override
  public int hashCode() {
    return Objects.hash(booking);
  }

  @Override
  public String toString() {
    return "BookingEvent{" +
        "booking=" + booking +
        '}';
  }
}
