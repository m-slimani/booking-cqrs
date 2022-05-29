package com.cola.booking.query.infrastructure.opening;

import com.cola.booking.query.domain.opening.Opening;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OpeningStoreImpl implements OpeningStore {

  private OpeningProperties openingProperties;
  private RoomProperties roomProperties;

  @Override
  public List<Opening> getOpenings(LocalDate date) {
    if (StringUtils.isBlank(openingProperties.getStartTime())
        || StringUtils.isBlank(openingProperties.getEndTime())
        || 0 == openingProperties.getDuration()) {
      return Collections.emptyList();
    }

    LocalTime startTime = LocalTime.parse(openingProperties.getStartTime());
    LocalTime endTime = LocalTime.parse(openingProperties.getEndTime());

    List<Opening> openings = new ArrayList<>();
    Arrays.stream(roomProperties.getNames().split(","))
        .filter(StringUtils::isNotBlank)
        .forEach(
            name ->
                IntStream.range(
                        startTime.get(ChronoField.HOUR_OF_DAY),
                        endTime.get(ChronoField.HOUR_OF_DAY))
                    // .filter(i -> i > 0)
                    .forEach(
                        (int value) ->
                            openings.add(
                                Opening.builder()
                                    .roomNumber(name)
                                    .startDateTime(
                                        LocalDateTime.of(date, LocalTime.of(value, 0, 0)))
                                    .build())));

    return openings;
  }
}
