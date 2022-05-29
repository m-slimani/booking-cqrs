package com.cola.booking.query.infrastructure.history;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingHistoryRepository extends JpaRepository<BookingHistoryEntity, Long> {
  List<BookingHistoryEntity> findByUserIdAndStatus(Long userId, String status);

  List<BookingHistoryEntity> findByUserId(Long userId);

  List<BookingHistoryEntity> findByStartDateTimeBetweenAndStatus(
      LocalDateTime start, LocalDateTime end, String status);
}
