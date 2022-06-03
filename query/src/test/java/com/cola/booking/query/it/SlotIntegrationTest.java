package com.cola.booking.query.it;

import static com.cola.booking.query.DatesUtils.JANUARY_FIRST;
import static com.cola.booking.query.DatesUtils.JANUARY_FIRST_EIGHT_AM;
import static com.cola.booking.query.domain.AvailabilityStatusEnum.BOOKED;
import static com.cola.booking.query.domain.AvailabilityStatusEnum.FREE;

import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.infrastructure.history.BookingHistoryEntity;
import com.cola.booking.query.infrastructure.history.BookingHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** configuration setting is made for one opening starting at 8:00 */
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
      "opening.startTime=08:00:00",
      "opening.endTime=09:00:00",
      "opening.duration=60",
      "room.names=C01"
    })
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SlotIntegrationTest {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private BookingHistoryRepository bookingHistoryRepository;

  @Test
  @DisplayName("1) available slot should return a slot")
  void emptyListOfSlots() throws Exception {

    MvcResult result = get("/slots", JANUARY_FIRST, "free");
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response =
        Arrays.asList(
            objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    Assertions.assertThat(response).size().isEqualTo(1);
    SlotR slotR = response.get(0);
    //    Assertions.assertThat(slotR.getId()).isEqualTo(1L);
    Assertions.assertThat(slotR.getRoomNumber()).isEqualTo("C01");
    Assertions.assertThat(slotR.getStatus()).isEqualTo(FREE.getValue());
    Assertions.assertThat(slotR.getStartDateTime()).isEqualTo("2022-01-01T08:00:00");
  }

  @Test
  @DisplayName("2) booked slot should not be available")
  void getFreeSlots() throws Exception {

    // book the available opening
    bookingHistoryRepository.save(buildEntity("C01", JANUARY_FIRST_EIGHT_AM));

    MvcResult result = get("/slots", JANUARY_FIRST, "free");
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response =
        Arrays.asList(
            objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    // no slot
    Assertions.assertThat(response).isEmpty();
  }

  @Test
  @DisplayName("3) slots of the day should be available slots")
  void getFreeSlotsInTheDay() throws Exception {

    MvcResult result = get("/slots", JANUARY_FIRST, "free");
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response =
        Arrays.asList(
            objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    Assertions.assertThat(response).size().isEqualTo(1);
  }

  private MvcResult get(String url, LocalDate inputDate, String status) throws Exception {
    return mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .param("date", DATE_TIME_FORMATTER.format(inputDate))
                .param("status", status)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
  }

  private BookingHistoryEntity buildEntity(String roomNumber, LocalDateTime startDateTime) {
    return BookingHistoryEntity.builder()
        .roomNumber(roomNumber)
        .status(BOOKED.getValue())
        .userId(1L)
        .startDateTime(startDateTime)
        .build();
  }
}
