package com.cola.booking.command.domain;

import static com.cola.booking.command.domain.BookingStatusEnum.BOOKED_STATUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cola.booking.command.domain.event.BookingCanceledEvent;
import com.cola.booking.command.domain.event.BookingEvent;
import com.cola.booking.command.infrastructure.booking.BookingStore;
import com.cola.booking.command.infrastructure.booking.BookingStoreImpl;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class BookingServiceTest {

  private BookingService bookingService;

  private BookingStore bookingStore;

  @BeforeEach
  void setUp() {
    bookingStore = mock(BookingStoreImpl.class);
    bookingService = new BookingService(bookingStore);
  }

  @Test
  @DisplayName("1) null booking should throw exception")
  void nullBookingShouldReturnException() {
    assertThatThrownBy(() -> bookingService.save(null)).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("booking is mandatory");
  }

  @Test
  @DisplayName("2) null organiser number should throw exception")
  void noOrganiserNumberShouldReturnException() {
    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, null, "C01", 1L, new ArrayList<>(), BOOKED_STATUS.getValue());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("organiserNumber is mandatory");
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String roomNumber) {
    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, 1L, roomNumber, 1L, new ArrayList<>(), BOOKED_STATUS.getValue());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("roomNumber is mandatory");
  }

  @Test
  @DisplayName("4) null slot number should throw exception")
  void noSlotNumberShouldReturnException() {

    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, 1L, "C01", null, new ArrayList<>(), BOOKED_STATUS.getValue());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("slotNumber is mandatory");
  }

  @Test
  @DisplayName("5) booking should be saved and return new created id")
  void bookingShouldBeSavedAndReturnNewId() throws FunctionalException {
    Booking booking = new Booking(null, 1L, "C01", 1L, new ArrayList<>(), BOOKED_STATUS.getValue());
    Booking bookingWithId = new Booking(1L, 1L, "C01", 1L, new ArrayList<>(), BOOKED_STATUS.getValue());
    when(bookingStore.save(booking)).thenReturn(bookingWithId);

    Booking result = bookingService.save(booking);
    assertThat(booking).isNotNull();
    assertThat(result.getId()).isEqualTo(1);

  }

  @Test
  @DisplayName("6) cancel booking should cancel the booking and free the slot")
  void cancelBookingShouldCancelAndFreeTheSlot() throws FunctionalException, NotFoundException {

    Booking booking = new Booking(1L, 1L, "C01", 1L, new ArrayList<>(), BOOKED_STATUS.getValue());
    when(bookingStore.findById(booking.getId())).thenReturn(booking);
    doNothing().when(bookingStore).sendNotificationEvent(new BookingEvent(booking));

    bookingService.cancel(booking.getId());
    verify(bookingStore, times(1)).cancel(booking);
    verify(bookingStore, times(1)).sendNotificationEvent(new BookingCanceledEvent(booking));
  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }

}
