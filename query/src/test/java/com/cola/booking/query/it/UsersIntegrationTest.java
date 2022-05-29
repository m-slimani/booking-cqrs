package com.cola.booking.query.it;

import static com.cola.booking.query.domain.AvailabilityStatusEnum.BOOKED;
import static org.assertj.core.api.Assertions.assertThat;

import com.cola.booking.query.application.model.BookingR;
import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.infrastructure.history.BookingHistoryEntity;
import com.cola.booking.query.infrastructure.history.BookingHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private BookingHistoryRepository bookingHistoryRepository;

  @Test
  @DisplayName("1) no booked slots should return empty list")
  void emptyListOfBookings() throws Exception {

    MvcResult result = get("/users/{userId}/bookings", "1");
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response =
        Arrays.asList(
            objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    assertThat(response).size().isEqualTo(0);
  }

  @Test
  @DisplayName("2) one history should return list of one")
  void historyListOfBookings() throws Exception {

    bookingHistoryRepository.save(buildHistory());

    MvcResult result = get("/users/{userId}/bookings", "1");
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<BookingR> response =
        Arrays.asList(
            objectMapper.readValue(result.getResponse().getContentAsString(), BookingR[].class));
    assertThat(response).size().isEqualTo(1);
    assertThat(response.get(0)).isEqualTo(buildBookingR());
  }

  private BookingR buildBookingR() {
    BookingR bookingR = new BookingR();
    bookingR.setId(1);
    bookingR.setUserId(1);
    bookingR.setRoomNumber("C01");
    bookingR.setStatus(BOOKED.getValue());
    bookingR.setParticipants(Arrays.asList("Eric", "Marc"));
    return bookingR;
  }

  private BookingHistoryEntity buildHistory() {
    return BookingHistoryEntity.builder()
        .id(1L)
        .userId(1L)
        .roomNumber("C01")
        .status(BOOKED.getValue())
        .participants("Eric,Marc")
        .build();
  }

  private MvcResult get(String url, String userId) throws Exception {
    return mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, userId).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
  }
}
