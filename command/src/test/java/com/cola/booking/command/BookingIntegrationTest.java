package com.cola.booking.command;

import com.cola.booking.command.application.model.BookingR;
import com.cola.booking.command.domain.BookingStatusEnum;
import com.cola.booking.command.infrastructure.booking.BookingEntity;
import com.cola.booking.command.infrastructure.booking.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookingRepository bookingRepository;

  @Test
  @DisplayName("1) empty booking should return exception with bad request status")
  void emptyBooking() throws Exception {

    BookingR requestBody = null;

    MvcResult result = post("/bookings", requestBody);
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }


  @Test
  @DisplayName("2) save booking should return success status")
  void saveBooking() throws Exception {

    BookingR requestBody = buildBookingR();

    MvcResult result = post("/bookings", requestBody);

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    BookingR response = objectMapper.readValue(result.getResponse().getContentAsString(), BookingR.class);
    checkAttributes(response);
  }

  @Test
  @DisplayName("3) save existing booking should return exception")
  void saveExistingBooking() throws Exception {

    bookingRepository.save(buildEntity());

    BookingR requestBody = buildBookingR();

    MvcResult result = post("/bookings", requestBody);

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("4) cancel booking should cancel and return success status")
  void cancelBooking() throws Exception {

    bookingRepository.save(buildEntity());

    MvcResult result = delete("/bookings/{id}", 1L);

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(204);
    Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo("Booking canceled");
  }

  private BookingEntity buildEntity() {
    return BookingEntity.builder()
        .id(1L)
        .organiserNumber(1L)
        .roomNumber("C01")
        .slotNumber(1L)
        .participants("John,Marc")
        .status(BookingStatusEnum.BOOKED_STATUS.getValue())
        .build();
  }

  private void checkAttributes(BookingR response) {
    Assertions.assertThat(response.getId()).isEqualTo(1);
    Assertions.assertThat(response.getOrganiserNumber()).isEqualTo(1);
    Assertions.assertThat(response.getRoomNumber()).isEqualTo("C01");
    Assertions.assertThat(response.getSlotNumber()).isEqualTo(1);
    Assertions.assertThat(response.getParticipants()).isEqualTo(Arrays.asList("John", "Marc"));
    Assertions.assertThat(response.getStatus()).isEqualTo(BookingStatusEnum.BOOKED_STATUS.getValue());
  }

  private MvcResult post(String url, BookingR requestBody) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .post(url)
        .content(objectMapper.writeValueAsString(requestBody))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
  }

  private MvcResult delete(String url, Long bookingId) throws Exception {
    return this.mockMvc.perform(MockMvcRequestBuilders
        .delete(url, bookingId)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
  }

  private BookingR buildBookingR() {
    BookingR bookingR = new BookingR();
    bookingR.setOrganiserNumber(1);
    bookingR.setRoomNumber("C01");
    bookingR.setSlotNumber(1);
    bookingR.setParticipants(Arrays.asList("John", "Marc"));
    return bookingR;
  }

}
