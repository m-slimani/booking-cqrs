package com.cola.booking.query.infrastructure.init;

import com.cola.booking.query.infrastructure.slot.SlotEntity;
import com.cola.booking.query.infrastructure.slot.SlotRepository;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

  private SlotProperties slotProperties;
  private RoomProperties roomProperties;
  private SlotRepository slotRepository;

  public void run(ApplicationArguments args) {
    LocalTime startTime = LocalTime.parse(slotProperties.getOpeningStartTime());
    LocalTime endTime = LocalTime.parse(slotProperties.getOpeningEndTime());

    slotsInit(roomProperties.getCokeNames(), startTime, endTime);
    slotsInit(roomProperties.getPepsiNames(), startTime, endTime);
  }

  private void slotsInit(String roomNames, LocalTime startTime, LocalTime endTime) {
    Arrays.stream(roomNames.split(","))
        .filter(StringUtils::isNotBlank)
        .forEach(
            name ->
                IntStream.range(startTime.get(ChronoField.HOUR_OF_DAY), endTime.get(ChronoField.HOUR_OF_DAY))
                    .filter(i -> i > 0)
                    .forEach(
                        (int value) -> slotRepository.save(
                            SlotEntity.builder()
                                .slotNumber(value)
                                .roomNumber(name)
                                .startTime(LocalTime.of(value, 0))
                                .status("free")
                                .build()))
        );

  }
}
