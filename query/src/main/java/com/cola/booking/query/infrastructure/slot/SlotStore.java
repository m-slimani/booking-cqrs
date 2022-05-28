package com.cola.booking.query.infrastructure.slot;

import com.cola.booking.query.domain.Slot;
import java.util.List;

public interface SlotStore {

  List<Slot> findSlotsByStatus(String status);
}
