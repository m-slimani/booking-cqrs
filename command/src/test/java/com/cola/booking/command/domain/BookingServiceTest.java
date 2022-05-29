package com.cola.booking.command.domain;

import static com.cola.booking.command.domain.BookingStatusEnum.BOOKED_STATUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cola.booking.command.domain.event.BookingEvent;
import com.cola.booking.command.infrastructure.booking.BookingStore;
import com.cola.booking.command.infrastructure.booking.BookingStoreImpl;
import java.time.LocalDateTime;
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

  private static final LocalDateTime JANUARY_FIRST_EIGHT_AM = LocalDateTime.of(2022, 1, 1, 8, 0, 0);

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
    assertThatThrownBy(() -> bookingService.save(null))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("booking is mandatory");
  }

  @Test
  @DisplayName("2) null userId number should throw exception")
  void noOrganiserNumberShouldReturnException() {
    assertThatThrownBy(
            () ->
                bookingService.save(
                    Booking.builder()
                        .id(1L)
                        .userId(null)
                        .roomNumber("C01")
                        .startDateTime(JANUARY_FIRST_EIGHT_AM)
                        .status(BOOKED_STATUS.getValue())
                        .participants(new ArrayList<>())
                        .build()))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("userId is mandatory");
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String roomNumber) {
    assertThatThrownBy(
            () ->
                bookingService.save(
                    Booking.builder()
                        .id(1L)
                        .userId(1L)
                        .roomNumber(roomNumber)
                        .startDateTime(JANUARY_FIRST_EIGHT_AM)
                        .status(BOOKED_STATUS.getValue())
                        .participants(new ArrayList<>())
                        .build()))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("roomNumber is mandatory");
  }

  @Test
  @DisplayName("4) null startDateTime number should throw exception")
  void noSlotNumberShouldReturnException() {

    assertThatThrownBy(
            () ->
                bookingService.save(
                    Booking.builder()
                        .id(1L)
                        .userId(1L)
                        .roomNumber("C01")
                        .startDateTime(null)
                        .status(BOOKED_STATUS.getValue())
                        .participants(new ArrayList<>())
                        .build()))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("startDatetime is mandatory");
  }

  @Test
  @DisplayName("5) booking should be saved and return new created id")
  void bookingShouldBeSavedAndReturnNewId() throws FunctionalException {
    Booking toCreate =
        Booking.builder()
            .id(null)
            .userId(1L)
            .roomNumber("C01")
            .startDateTime(JANUARY_FIRST_EIGHT_AM)
            .status(BOOKED_STATUS.getValue())
            .participants(new ArrayList<>())
            .build();

    Booking created =
        Booking.builder()
            .id(1L)
            .userId(1L)
            .roomNumber("C01")
            .startDateTime(JANUARY_FIRST_EIGHT_AM)
            .status(BOOKED_STATUS.getValue())
            .participants(new ArrayList<>())
            .build();
    when(bookingStore.save(toCreate)).thenReturn(created);

    Booking result = bookingService.save(toCreate);
    assertThat(toCreate).isNotNull();
    assertThat(result.getId()).isEqualTo(1);
  }

  @Test
  @DisplayName("6) cancel booking should cancel the booking and free the slot")
  void cancelBookingShouldCancelAndFreeTheSlot() throws FunctionalException, NotFoundException {

    Booking booking =
        Booking.builder()
            .id(1L)
            .userId(1L)
            .roomNumber("C01")
            .startDateTime(JANUARY_FIRST_EIGHT_AM)
            .status(BOOKED_STATUS.getValue())
            .participants(new ArrayList<>())
            .build();
    when(bookingStore.findById(booking.getId())).thenReturn(booking);
    doNothing()
        .when(bookingStore)
        .sendNotificationEvent(
            BookingEvent.builder().booking(booking).type("create").build());

    bookingService.cancel(booking.getId());
    verify(bookingStore, times(1)).cancel(booking);
    verify(bookingStore, times(1))
        .sendNotificationEvent(
            BookingEvent.builder().booking(booking).type("cancel").build());
  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }
}
