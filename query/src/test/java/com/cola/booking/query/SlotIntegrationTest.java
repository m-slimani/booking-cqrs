package com.cola.booking.query;

import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.domain.SlotStatusEnum;
import com.cola.booking.query.infrastructure.slot.SlotEntity;
import com.cola.booking.query.infrastructure.slot.SlotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SlotIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private SlotRepository slotRepository;

  @Test
  @DisplayName("1) no available slots should return empty list of slots")
  void emptyListOfSlots() throws Exception {

    slotRepository.deleteAll();

    MvcResult result = get("/slots", "free");
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    Assertions.assertThat(response).size().isEqualTo(0);
  }

  @Test
  @DisplayName("2) get available slots should return all available slots")
  void getFreeSlots() throws Exception {

    SlotEntity slotEntity = buildSlotEntity();
    slotRepository.save(slotEntity);

    MvcResult result = get("/slots", "free");
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    List<SlotR> response = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), SlotR[].class));
    Assertions.assertThat(response).size().isEqualTo(1);
    SlotR slotR = response.get(0);
    Assertions.assertThat(slotR.getId()).isEqualTo(1L);
    Assertions.assertThat(slotR.getRoomNumber()).isEqualTo("C01");
    Assertions.assertThat(slotR.getSlotNumber()).isEqualTo(1);
    Assertions.assertThat(slotR.getStartTime()).isEqualTo("08:00:00");
  }

  private SlotEntity buildSlotEntity() {
    return SlotEntity.builder()
        .id(1L)
        .roomNumber("C01")
        .slotNumber(1)
        .startTime(LocalTime.of(8, 0))
        .status(SlotStatusEnum.FREE.getValue())
        .build();
  }


  private MvcResult get(String url, String status) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .get(url)
        .param("status", status)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
  }


}
