package com.cola.booking.query.infrastructure.opening;

import static com.cola.booking.query.DatesUtils.JANUARY_FIRST;
import static org.assertj.core.api.Assertions.assertThat;

import com.cola.booking.query.domain.opening.Opening;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpeningIntegrationTest {

  @Autowired private OpeningProperties openingProperties;
  @Autowired private RoomProperties roomProperties;
  private OpeningStoreImpl openingStoreImpl;

  @BeforeEach
  void setUp() {
    openingStoreImpl = new OpeningStoreImpl(openingProperties, roomProperties);
  }

  @Test
  @DisplayName("1) no opening configuration should return no available slots")
  void noOpeningShouldReturnEmptyList() {
    openingProperties.setStartTime("");
    openingProperties.setEndTime("");
    openingProperties.setDuration(60);
    assertThat(openingStoreImpl.getOpenings(JANUARY_FIRST)).isEmpty();
  }

  @Test
  @DisplayName("2) one opening configuration should return one slot")
  void oneOpeningShouldReturn() {
    openingProperties.setStartTime("08:00:00");
    openingProperties.setEndTime("09:00:00");
    openingProperties.setDuration(60);
    roomProperties.setNames("C01");

    List<Opening> result = openingStoreImpl.getOpenings(JANUARY_FIRST);
    assertThat(result).size().isEqualTo(1);
    assertThat(result.get(0).getStartDateTime())
        .isEqualTo(LocalDateTime.of(JANUARY_FIRST, LocalTime.of(8, 0, 0)));
  }

  @Test
  @DisplayName("3) many openings configuration should return many slots")
  void manyOpeningsShouldReturn() {
    openingProperties.setStartTime("08:00:00");
    openingProperties.setEndTime("10:00:00");
    openingProperties.setDuration(60);
    roomProperties.setNames("C01");

    List<Opening> result = openingStoreImpl.getOpenings(JANUARY_FIRST);
    assertThat(result).size().isEqualTo(2);
    assertThat(result.get(0).getStartDateTime())
        .isEqualTo(LocalDateTime.of(JANUARY_FIRST, LocalTime.of(8, 0, 0)));
    assertThat(result.get(1).getStartDateTime())
        .isEqualTo(LocalDateTime.of(JANUARY_FIRST, LocalTime.of(9, 0, 0)));
  }
}
