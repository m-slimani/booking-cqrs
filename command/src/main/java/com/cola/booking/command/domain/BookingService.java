package com.cola.booking.command.domain;

import com.cola.booking.command.domain.event.BookingCanceledEvent;
import com.cola.booking.command.domain.event.BookingCreatedEvent;
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
    if (Objects.isNull(booking.getOrganiserNumber())) {
      throw new FunctionalException("organiserNumber is mandatory");
    }
    if (StringUtils.isBlank(booking.getRoomNumber())) {
      throw new FunctionalException("roomNumber is mandatory");
    }
    if (Objects.isNull(booking.getSlotNumber())) {
      throw new FunctionalException("slotNumber is mandatory");
    }
    List<Booking> existing = bookingStore.findByRoomNumberAndSlotNumber(booking.getRoomNumber(), booking.getSlotNumber());

    if (existing.isEmpty()) {
      Booking created = bookingStore.save(booking);
      BookingEvent bookingCreatedEvent = new BookingCreatedEvent(created);
      bookingStore.sendNotificationEvent(bookingCreatedEvent);
      return created;
    } else {
      throw new FunctionalException("Slot already booked");
    }


  }

  @Transactional
  public void cancel(Long bookingId) throws NotFoundException, FunctionalException {
    Booking toDelete = bookingStore.findById(bookingId);
    bookingStore.cancel(toDelete);
    BookingEvent bookingCanceledEvent = new BookingCanceledEvent(toDelete);
    bookingStore.sendNotificationEvent(bookingCanceledEvent);
  }
}
