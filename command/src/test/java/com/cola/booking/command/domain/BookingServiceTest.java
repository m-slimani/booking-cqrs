package com.cola.booking.command.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cola.booking.command.infrastructure.BookingStore;
import com.cola.booking.command.infrastructure.BookingStoreImpl;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class BookingServiceTest {

  private BookingService bookingService;

  private BookingStore bookingStore;

  @BeforeEach
  void setUp() {
    bookingStore = Mockito.mock(BookingStoreImpl.class);
    bookingService = new BookingService(bookingStore);
  }

  @Test
  @DisplayName("1) null booking should throw exception")
  void nullBookingShouldReturnException() {
    assertThatThrownBy(() -> {
      bookingService.save(null);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("booking is mandatory");
  }

  @Test
  @DisplayName("2) null organiser number should throw exception")
  void noOrganiserNumberShouldReturnException() {
    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, null, "C01", 1, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("organiserNumber is mandatory");
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String roomNumber) {
    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, 1, roomNumber, 1, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("roomNumber is mandatory");
  }

  @Test
  @DisplayName("4) null slot number should throw exception")
  void noSlotNumberShouldReturnException() {

    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, null, "C01", null, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("slotNumber is mandatory");
  }

  @Test
  @DisplayName("5) booking should be saved and return new created id")
  void bookingShouldBeSavedAndReturnNewId() throws FunctionalException {
    Booking booking = new Booking(null, 1, "C01", 1, new ArrayList<>());
    Booking bookingWithId = new Booking(1L, 1, "C01", 1, new ArrayList<>());
    Mockito.when(bookingStore.save(booking)).thenReturn(bookingWithId);

    Booking result = bookingService.save(booking);
    assertThat(booking).isNotNull();
    assertThat(result.getBookingId()).isEqualTo(1);

  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }

}
