package com.cola.booking.query.application;

import com.cola.booking.query.application.model.SlotR;
import com.cola.booking.query.domain.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SlotRMapper {

  SlotRMapper SLOT_R_MAPPER = Mappers.getMapper(SlotRMapper.class);

  @Mapping(source = "startTime", target = "startTime")
  SlotR toSlotR(Slot slot);
}
