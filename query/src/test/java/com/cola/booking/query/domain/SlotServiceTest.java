package com.cola.booking.query.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cola.booking.query.domain.opening.Opening;
import com.cola.booking.query.infrastructure.history.BookingHistoryStore;
import com.cola.booking.query.infrastructure.opening.OpeningStore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlotServiceTest {
  private static final LocalDate JANUARY_FIRST = LocalDate.of(2022, 1, 1);
  private static final LocalDateTime JANUARY_FIRST_EIGHT_AM = LocalDateTime.of(2022, 1, 1, 8, 0, 0);

  private SlotService slotService;

  private BookingHistoryStore bookingHistoryStore;
  private OpeningStore openingStore;

  @BeforeEach
  void setUp() {
    bookingHistoryStore = mock(BookingHistoryStore.class);
    openingStore = mock(OpeningStore.class);
    slotService = new SlotService(bookingHistoryStore, openingStore);
  }

  @Test
  @DisplayName("1) no available slot should return empty list")
  void emptyList() {
    assertThat(slotService.getFreeSlots(JANUARY_FIRST)).isEmpty();
  }

  @Test
  @DisplayName("2) available slot should be returned")
  void listOfOne() {

    when(openingStore.getOpenings(JANUARY_FIRST))
        .thenReturn(Collections.singletonList(buildOpening()));

    List<Slot> availableSlots = slotService.getFreeSlots(JANUARY_FIRST);

    Slot expected = buildSlot();
    assertThat(availableSlots).isNotEmpty();
    assertThat(availableSlots).contains(expected);
  }

  private Slot buildSlot() {
    return Slot.builder()
        .roomNumber("C01")
        .startDateTime(JANUARY_FIRST_EIGHT_AM)
        .status(AvailabilityStatusEnum.FREE.getValue())
        .build();
  }

  private Opening buildOpening() {
    return Opening.builder().roomNumber("C01").startDateTime(JANUARY_FIRST_EIGHT_AM).build();
  }
}
