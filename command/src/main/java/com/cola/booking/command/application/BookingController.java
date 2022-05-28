package com.cola.booking.command.application;


import static com.cola.booking.command.application.BookingRMapper.BOOKING_R_MAPPER;

import com.cola.booking.command.application.api.BookingsApi;
import com.cola.booking.command.application.model.BookingR;
import com.cola.booking.command.domain.Booking;
import com.cola.booking.command.domain.BookingService;
import com.cola.booking.command.domain.FunctionalException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class BookingController implements BookingsApi {

  private BookingService bookingService;

  @Override
  public ResponseEntity<BookingR> postBookings(BookingR bookingR) {
    try {
      Booking booking = bookingService.save(BOOKING_R_MAPPER.bookingRToBooking(bookingR));
      return new ResponseEntity(BOOKING_R_MAPPER.bookingToBookingR(booking), HttpStatus.OK);
    } catch (FunctionalException e) {
       log.error(e.getMessage(), e);
      return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<Void> deleteBookings(Integer bookingId) {
      try {
      bookingService.cancel(Long.valueOf(bookingId));
    } catch (FunctionalException | NotFoundException e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity("Booking canceled", HttpStatus.NO_CONTENT);
  }
}
