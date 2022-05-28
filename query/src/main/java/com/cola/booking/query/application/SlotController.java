package com.cola.booking.query.application;

import static com.cola.booking.query.application.SlotRMapper.SLOT_R_MAPPER;

import com.cola.booking.query.application.api.SlotsApi;
import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.domain.SlotService;
import com.cola.booking.query.domain.SlotStatusEnum;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SlotController implements SlotsApi {

  private SlotService slotService;

  @Override
  public ResponseEntity<List<SlotR>> getSlots(String status) {
    if (SlotStatusEnum.FREE.getValue().equals(status)) {
      return new ResponseEntity(
          slotService.getFreeSlots().stream()
              .map(SLOT_R_MAPPER::toSlotR)
              .collect(Collectors.toList()),
          HttpStatus.OK);
    }
    return new ResponseEntity("Status not allowed", HttpStatus.BAD_REQUEST);
  }
}
