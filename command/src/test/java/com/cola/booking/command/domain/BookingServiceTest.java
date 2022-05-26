package com.cola.booking.command.domain;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BookingServiceTest {

  private BookingService bookingService;

  @BeforeEach
  void setUp() {
    bookingService = new BookingService();
  }

  @Test
  @DisplayName("1) null booking should throw exception")
  void nullBookingShouldReturnException() {
    FunctionalException exception = Assertions.assertThrows(FunctionalException.class, () -> {
      bookingService.save(null);
    });
    Assertions.assertEquals("booking is mandatory", exception.getMessage());
  }

  @Test
  @DisplayName("2) null organiser number should throw exception")
  void noOrganiserNumberShouldReturnException() {
    FunctionalException exception = Assertions.assertThrows(FunctionalException.class, () -> {
      Booking booking = new Booking(null, "C01", 1, new ArrayList<>());
      bookingService.save(booking);
    });
    Assertions.assertEquals("organiserNumber is mandatory", exception.getMessage());
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String roomNumber) {
    FunctionalException exception = Assertions.assertThrows(FunctionalException.class, () -> {
      Booking booking = new Booking(1, roomNumber, 1, new ArrayList<>());
      bookingService.save(booking);
    });
    Assertions.assertEquals("roomNumber is mandatory", exception.getMessage());
  }

  @Test
  @DisplayName("4) null slot number should throw exception")
  void noSlotNumberShouldReturnException() {
    FunctionalException exception = Assertions.assertThrows(FunctionalException.class, () -> {
      Booking booking = new Booking(1, "C01", null, new ArrayList<>());
      bookingService.save(booking);
    });
    Assertions.assertEquals("slotNumber is mandatory", exception.getMessage());
  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }

}
