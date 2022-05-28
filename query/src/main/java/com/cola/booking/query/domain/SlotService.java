package com.cola.booking.query.domain;

import com.cola.booking.query.infrastructure.slot.SlotStore;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlotService {

  private SlotStore slotStore;

  public List<Slot> getFreeSlots() {
    return slotStore.findSlotsByStatus(SlotStatusEnum.FREE.getValue());
  }
}
