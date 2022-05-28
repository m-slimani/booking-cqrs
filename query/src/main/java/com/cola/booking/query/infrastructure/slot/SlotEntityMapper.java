package com.cola.booking.query.infrastructure.slot;

import com.cola.booking.query.domain.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SlotEntityMapper {

  SlotEntityMapper SLOT_ENTITY_MAPPER = Mappers.getMapper(SlotEntityMapper.class);

 // @Mapping(source = "startTime", target = "startTime")
  Slot fromEntity(SlotEntity slotEntity);
}
