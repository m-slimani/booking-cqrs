package com.cola.booking.command.domain;

import static com.cola.booking.command.DatesUtils.JANUARY_FIRST_EIGHT_AM;
import static com.cola.booking.command.domain.BookingStatusEnum.BOOKED_STATUS;
import static com.cola.booking.command.domain.event.EventTypeEnum.CREATE;
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
    assertThatThrownBy(() -> saveBooking("C01", JANUARY_FIRST_EIGHT_AM, null))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("userId is mandatory");
  }

  @ParameterizedTest
  @DisplayName("3) blank or null room number should throw exception")
  @MethodSource("blankOrNullStrings")
  void noRoomNumberShouldReturnException(String invalidRoomNumber) {
    assertThatThrownBy(() -> saveBooking(invalidRoomNumber, JANUARY_FIRST_EIGHT_AM, 1L))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("roomNumber is mandatory");
  }

  @Test
  @DisplayName("4) null startDateTime number should throw exception")
  void noSlotNumberShouldReturnException() {

    assertThatThrownBy(() -> saveBooking("C01", null, 1L))
        .isInstanceOf(FunctionalException.class)
        .hasMessageContaining("startDatetime is mandatory");
  }

  private Booking saveBooking(String roomNumber, LocalDateTime startDateTime, Long userId)
      throws FunctionalException {
    return bookingService.save(buildBooking(1L, userId, roomNumber, startDateTime));
  }

  @Test
  @DisplayName("5) booking should be saved and return new created id")
  void bookingShouldBeSavedAndReturnNewId() throws FunctionalException {
    Booking toCreate = buildBooking(null, 1L, "C01", JANUARY_FIRST_EIGHT_AM);

    Booking created = buildBooking(1L, 1L, "C01", JANUARY_FIRST_EIGHT_AM);

    when(bookingStore.save(toCreate)).thenReturn(created);

    Booking result = bookingService.save(toCreate);
    assertThat(toCreate).isNotNull();
    assertThat(result.getId()).isEqualTo(1);
  }

  @Test
  @DisplayName("6) cancel booking should cancel the booking and free the slot")
  void cancelBookingShouldCancelAndFreeTheSlot() throws FunctionalException, NotFoundException {

    Booking booking = buildBooking(1L, 1L, "C01", JANUARY_FIRST_EIGHT_AM);

    when(bookingStore.findById(booking.getId())).thenReturn(booking);
    doNothing()
        .when(bookingStore)
        .sendNotificationEvent(
            BookingEvent.builder().booking(booking).type(CREATE.getValue()).build());

    bookingService.cancel(booking.getId());
    verify(bookingStore, times(1)).cancel(booking);
    verify(bookingStore, times(1))
        .sendNotificationEvent(BookingEvent.builder().booking(booking).type("cancel").build());
  }

  static Stream<String> blankOrNullStrings() {
    return Stream.of("", " ", null);
  }

  private Booking buildBooking(
      Long id, Long userId, String roomNumber, LocalDateTime startDateTime) {
    return Booking.builder()
        .id(id)
        .userId(userId)
        .roomNumber(roomNumber)
        .startDateTime(startDateTime)
        .status(BOOKED_STATUS.getValue())
        .participants(new ArrayList<>())
        .build();
  }
}
