package com.cola.booking.query.infrastructure.slot;

import static com.cola.booking.query.domain.SlotStatusEnum.FREE;
import static com.cola.booking.query.infrastructure.slot.SlotEntityMapper.*;

import com.cola.booking.query.domain.Slot;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlotMapperTest {

  @Test
  @DisplayName("1) null value should return null")
  void nullValue() {
    Assertions.assertThat(SLOT_ENTITY_MAPPER.fromEntity(null)).isNull();
  }

  @Test
  @DisplayName("2) entity value should be mapped")
  void fromEntity() {
    SlotEntity slotEntity = buildEntity();
    Slot expected = Slot.builder()
        .id(1L)
        .roomNumber("C01")
        .slotNumber(1)
        .status(FREE.getValue())
        .startTime(LocalTime.of(8, 0))
        .build();
    Assertions.assertThat(SLOT_ENTITY_MAPPER.fromEntity(slotEntity)).isEqualTo(expected);
  }

  private SlotEntity buildEntity() {
    return SlotEntity.builder()
        .id(1L)
        .roomNumber("C01")
        .slotNumber(1)
        .startTime(LocalTime.of(8, 0))
        .status(FREE.getValue())
        .build();
  }

}