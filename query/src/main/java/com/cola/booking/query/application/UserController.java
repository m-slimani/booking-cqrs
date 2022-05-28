package com.cola.booking.query.application;

import static com.cola.booking.query.application.BookingRMapper.BOOKING_R_MAPPER;

import com.cola.booking.query.application.api.UsersApi;
import com.cola.booking.query.application.model.BookingR;
import com.cola.booking.query.domain.BookingHistoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UsersApi {

  private BookingHistoryService bookingHistoryService;

  @Override
  public ResponseEntity<List<BookingR>> getBookingsByUserId(Integer userId, String status) {
    return new ResponseEntity(
        bookingHistoryService.getHistory(userId, status).stream()
            .map(BOOKING_R_MAPPER::toBookingR)
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }
}
