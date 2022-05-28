package com.cola.booking.query.infrastructure.slot;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<SlotEntity, Long> {

  List<SlotEntity> findSlotsByStatus(String status);
}
