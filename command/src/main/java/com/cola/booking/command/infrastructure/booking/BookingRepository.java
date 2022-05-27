package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

  List<BookingEntity> findByRoomNumberAndSlotNumber(String roomNumber, Long slotNumber);
}
