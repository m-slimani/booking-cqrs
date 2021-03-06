package com.cola.booking.command.domain;

import static com.cola.booking.command.domain.event.EventTypeEnum.CREATE;

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

    checkInputs(booking);

    List<Booking> existing =
        bookingStore.findBookings(booking.getRoomNumber(), booking.getStartDateTime());

    if (existing.isEmpty()) {
      Booking created = bookingStore.save(booking);
      bookingStore.sendNotificationEvent(
          BookingEvent.builder().booking(created).type(CREATE.getValue()).build());
      return created;
    } else {
      throw new FunctionalException(String.format("This room: [%s], and date: [%s] is already booked", booking.getRoomNumber(),
          booking.getStartDateTime().toString()));
    }
  }

  private void checkInputs(Booking booking) throws FunctionalException {
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
  }

  @Transactional
  public void cancel(Long bookingId) throws NotFoundException, FunctionalException {
    Booking toDelete = bookingStore.findById(bookingId);
    bookingStore.cancel(toDelete);
    bookingStore.sendNotificationEvent(
        BookingEvent.builder().booking(toDelete).type("cancel").build());
  }
}
