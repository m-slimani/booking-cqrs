package com.cola.booking.query.application;

import static com.cola.booking.query.application.SlotRMapper.SLOT_R_MAPPER;
import static com.cola.booking.query.domain.AvailabilityStatusEnum.FREE;

import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.domain.Slot;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlotRMapperTest {

  @Test
  @DisplayName("1) null value should return null")
  void nullValue() {
    Assertions.assertThat(SLOT_R_MAPPER.toSlotR(null)).isNull();
  }

  @Test
  @DisplayName("2) slot value should be mapped")
  void fromEntity() {
    Slot slot = buildSlot();
    SlotR expected = buildSlotR();

    Assertions.assertThat(SLOT_R_MAPPER.toSlotR(slot)).isEqualTo(expected);
  }

  private SlotR buildSlotR() {
    SlotR slotR = new SlotR();
    slotR.setRoomNumber("C01");
    slotR.setStatus(FREE.getValue());
    slotR.setStartDateTime("2022-01-01T08:00:00");
    return slotR;
  }

  private Slot buildSlot() {
    return Slot.builder()
        .roomNumber("C01")
        .status(FREE.getValue())
        .startDateTime(LocalDateTime.of(2022, 1, 1, 8, 0, 0))
        .build();
  }
}
