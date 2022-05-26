package com.cola.booking.command.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.cola.booking.command.infrastructure.BookingStore;
import com.cola.booking.command.infrastructure.BookingStoreImpl;
import com.cola.booking.command.infrastructure.SlotStore;
import com.cola.booking.command.infrastructure.SlotStoreImpl;
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
  private SlotStore slotStore;

  @BeforeEach
  void setUp() {
    bookingStore = mock(BookingStoreImpl.class);
    slotStore = mock(SlotStoreImpl.class);
    bookingService = new BookingService(bookingStore, slotStore);
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
      Booking booking = new Booking(null, null, "C01", 1L, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("organiserNumber is mandatory");
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String roomNumber) {
    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, 1, roomNumber, 1L, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("roomNumber is mandatory");
  }

  @Test
  @DisplayName("4) null slot number should throw exception")
  void noSlotNumberShouldReturnException() {

    assertThatThrownBy(() -> {
      Booking booking = new Booking(null, 1, "C01", null, new ArrayList<>());
      bookingService.save(booking);
    }).isInstanceOf(FunctionalException.class)
        .hasMessageContaining("slotNumber is mandatory");
  }

  @Test
  @DisplayName("5) booking should be saved and return new created id")
  void bookingShouldBeSavedAndReturnNewId() throws FunctionalException {
    Booking booking = new Booking(null, 1, "C01", 1L, new ArrayList<>());
    Booking bookingWithId = new Booking(1L, 1, "C01", 1L, new ArrayList<>());
    when(bookingStore.save(booking)).thenReturn(bookingWithId);

    Booking result = bookingService.save(booking);
    assertThat(booking).isNotNull();
    assertThat(result.getBookingId()).isEqualTo(1);

  }

  @Test
  @DisplayName("6) cancel booking should cancel the booking and free the slot")
  void cancelBookingShouldCancelAndFreeTheSlot() throws FunctionalException {

    Booking booking = new Booking(1L, 1, "C01", 1L, new ArrayList<>());
    when(bookingStore.save(booking)).thenReturn(booking);
    doNothing().when(slotStore).freeUp(1L);

    bookingService.cancel(booking);
    verify(bookingStore, times(1)).cancel(booking);
    verify(slotStore, times(1)).freeUp(1L);


  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }

}
