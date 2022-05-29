package com.cola.booking.query.application;

import static com.cola.booking.query.application.SlotRMapper.SLOT_R_MAPPER;
import static com.cola.booking.query.domain.AvailabilityStatusEnum.FREE;

import com.cola.booking.query.application.api.SlotsApi;
import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.domain.SlotService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SlotController implements SlotsApi {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private SlotService slotService;

  @Override
  public ResponseEntity<List<SlotR>> getSlots(String status, String date) {

    if (StringUtils.isBlank(date)) {
      return getResponseEntity("input date is mandatory", HttpStatus.BAD_REQUEST);
    }
    if (!FREE.getValue().equals(status)) {
      return getResponseEntity(
          "Status value :[" + status + "], is not allowed", HttpStatus.BAD_REQUEST);
    }

    LocalDate inputDate;
    try {
      inputDate = LocalDate.parse(date, DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      return getResponseEntity(
          "Invalid input date format :[" + date + "]. Only [yyyy-MM-dd] is allowed",
          HttpStatus.BAD_REQUEST);
    }

    return getResponseEntity(
        slotService.getFreeSlots(inputDate).stream()
            .map(SLOT_R_MAPPER::toSlotR)
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }

  private ResponseEntity getResponseEntity(Object object, HttpStatus httpStatus) {
    return new ResponseEntity(object, httpStatus);
  }
}
