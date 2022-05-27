package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingEntityMapper {

  BookingEntityMapper INSTANCE = Mappers.getMapper(BookingEntityMapper.class);

  @Mapping(source = "participants", target = "participants")
  Booking fromEntity(BookingEntity bookingEntity);

  @Mapping(source = "organiserNumber", target = "organiserNumber")
  @Mapping(source = "participants", target = "participants")
  BookingEntity toEntity(Booking booking);

  default String mapParticipants(List<String> participants) {
    return String.join(",", participants);
  }

  default List<String> mapParticipants(String participants) {
    return StringUtils.isBlank(participants) ? Collections.emptyList()
        : Arrays.asList(participants.split(","));
  }
}