package com.cola.booking.command.domain;

import com.cola.booking.command.domain.event.BookingEvent;
import com.cola.booking.command.infrastructure.booking.BookingStore;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  private BookingStore bookingStore;

  public BookingService(BookingStore bookingStore) {
    this.bookingStore = bookingStore;
  }

  @Transactional
  public Booking save(Booking booking) throws FunctionalException {
    if (Objects.isNull(booking)) {
      throw new FunctionalException("booking is mandatory");
    }
    if (Objects.isNull(booking.getUserId())) {
      throw new FunctionalException("userId is mandatory");
    }
    if (StringUtils.isBlank(booking.getRoomNumber())) {
      throw new FunctionalException("roomNumber is mandatory");
    }
    if (Objects.isNull(booking.getStartDateTime())) {
      throw new FunctionalException("startDatetime is mandatory");
    }
    List<Booking> existing =
        bookingStore.findBookings(booking.getRoomNumber(), booking.getStartDateTime());

    if (existing.isEmpty()) {
      Booking created = bookingStore.save(booking);
      bookingStore.sendCreateNotificationEvent(
          BookingEvent.builder().booking(created).type("create").build());
      return created;
    } else {
      throw new FunctionalException("Slot already booked");
    }
  }

  @Transactional
  public void cancel(Long bookingId) throws NotFoundException, FunctionalException {
    Booking toDelete = bookingStore.findById(bookingId);
    bookingStore.cancel(toDelete);
    bookingStore.sendCancelNotificationEvent(
        BookingEvent.builder().booking(toDelete).type("cancel").build());
  }
}
