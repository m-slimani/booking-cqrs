package com.cola.booking.command.infrastructure.booking;

import static com.cola.booking.command.infrastructure.booking.BookingEntityMapper.INSTANCE;

import com.cola.booking.command.domain.Booking;
import com.cola.booking.command.domain.event.BookingEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingStoreImpl implements BookingStore {

  private BookingRepository bookingRepository;

  public BookingStoreImpl(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @Override
  public Booking findById(Long bookingId) throws NotFoundException {
    Optional<BookingEntity> optional = bookingRepository.findById(bookingId);
    if (optional.isPresent()) {
      return INSTANCE.fromEntity(optional.get());
    } else {
      throw new NotFoundException();
    }
  }

  @Override
  public List<Booking> findBookings(String roomNumber, LocalDateTime startDateTime) {
    return bookingRepository.findByRoomNumberAndStartDateTime(roomNumber, startDateTime).stream()
        .map(INSTANCE::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public Booking save(Booking booking) {
    return INSTANCE.fromEntity(bookingRepository.save(INSTANCE.toEntity(booking)));
  }

  @Override
  public void sendNotificationEvent(BookingEvent bookingEvent) {
    log.info("BookingEvent sent to queue is : " + bookingEvent);
  }

  @Override
  @Transactional
  public void cancel(Booking booking) {
    bookingRepository.delete(BookingEntityMapper.INSTANCE.toEntity(booking));
  }
}
