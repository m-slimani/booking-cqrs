package com.cola.booking.query.domain;

import static com.cola.booking.query.domain.SlotStatusEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cola.booking.query.infrastructure.slot.SlotStore;
import com.cola.booking.query.infrastructure.slot.SlotStoreImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlotServiceTest {

  private SlotService slotService;
  private SlotStore slotStore;

  @BeforeEach
  void setUp() {
    slotStore = mock(SlotStoreImpl.class);
    slotService = new SlotService(slotStore);
  }

  @Test
  @DisplayName("1) no available slot should return empty list")
  void emptyList() {
    assertThat(slotService.getFreeSlots()).isEmpty();
  }

  @Test
  @DisplayName("2) available slot should be returned")
  void listOfOne() {
    Slot expected = buildSlot();
    when(slotStore.findSlotsByStatus(FREE.getValue())).thenReturn(Collections.singletonList(expected));

    List<Slot> availableSlots = slotService.getFreeSlots();

    assertThat(availableSlots).isNotEmpty();
    assertThat(availableSlots).contains(expected);
  }

  private Slot buildSlot() {
    return Slot.builder()
        .id(1L)
        .roomNumber("C01")
        .slotNumber(1)
        .status("free")
        .build();
  }

}
