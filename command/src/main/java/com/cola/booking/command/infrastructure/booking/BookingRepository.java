package com.cola.booking.command.infrastructure.booking;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

  List<BookingEntity> findByRoomNumberAndStartDateTime(String roomNumber, LocalDateTime startDateTime);
}
