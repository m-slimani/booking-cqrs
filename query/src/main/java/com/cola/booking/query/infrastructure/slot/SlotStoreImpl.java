package com.cola.booking.query.infrastructure.slot;

import com.cola.booking.query.domain.Slot;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlotStoreImpl implements SlotStore {

  private SlotRepository slotRepository;

  @Override
  public List<Slot> findSlotsByStatus(String status) {
    return slotRepository.findSlotsByStatus(status).stream()
        .map(SlotEntityMapper.SLOT_ENTITY_MAPPER::fromEntity)
        .collect(Collectors.toList());
  }
}
