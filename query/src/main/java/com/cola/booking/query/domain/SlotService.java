package com.cola.booking.query.domain;

import static com.cola.booking.query.domain.AvailabilityStatusEnum.FREE;

import com.cola.booking.query.domain.opening.Opening;
import com.cola.booking.query.infrastructure.history.BookingHistoryStore;
import com.cola.booking.query.infrastructure.opening.OpeningStore;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlotService {

  private BookingHistoryStore bookingHistoryStore;
  private OpeningStore openingStore;

  public List<Slot> getFreeSlots(LocalDate date) {

    List<Opening> openings = openingStore.getOpenings(date);

    List<BookingHistory> historyList =
        bookingHistoryStore.findByStartDateTimeBetweenAndStatus(
            date, AvailabilityStatusEnum.BOOKED.getValue());

    return openings.stream()
        .filter(
            opening ->
                historyList.stream()
                    .noneMatch(
                        history ->
                            history.getRoomNumber().equals(opening.getRoomNumber())
                                && history.getStartDateTime().isEqual(opening.getStartDateTime())))
        .map(
            opening ->
                Slot.builder()
                    .roomNumber(opening.getRoomNumber())
                    .startDateTime(opening.getStartDateTime())
                    .status(FREE.getValue())
                    .build())
        .collect(Collectors.toList());
  }
}
