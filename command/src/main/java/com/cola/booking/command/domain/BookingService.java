package com.cola.booking.command.domain;

import com.cola.booking.command.infrastructure.BookingStore;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class BookingService {

  private BookingStore bookingStore;

  public BookingService(BookingStore bookingStore) {
    this.bookingStore = bookingStore;
  }

  public Booking save(Booking booking) throws FunctionalException {
    if (Objects.isNull(booking)) {
      throw new FunctionalException("booking is mandatory");
    }
    if (Objects.isNull(booking.getOrganiserNumber())) {
      throw new FunctionalException("organiserNumber is mandatory");
    }
    if (StringUtils.isBlank(booking.getRoomNumber())) {
      throw new FunctionalException("roomNumber is mandatory");
    }
    if (Objects.isNull(booking.getSlotNumber())) {
      throw new FunctionalException("slotNumber is mandatory");
    }
    return bookingStore.save(booking);
  }
}