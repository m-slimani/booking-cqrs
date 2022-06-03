package com.cola.booking.command.infrastructure.booking;

import com.cola.booking.command.domain.Booking;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingEntityMapper {

  BookingEntityMapper INSTANCE = Mappers.getMapper(BookingEntityMapper.class);

  @Mapping(source = "participants", target = "participants")
  Booking fromEntity(BookingEntity bookingEntity);

  @Mapping(source = "userId", target = "userId")
  @Mapping(source = "participants", target = "participants")
  BookingEntity toEntity(Booking booking);

  default String mapParticipants(List<String> participants) {
   if (Objects.isNull( participants) ||  participants.isEmpty()){
     return StringUtils.EMPTY;
   }
    return String.join(",", participants);
  }

  default List<String> mapParticipants(String participants) {
    return StringUtils.isBlank(participants) ? Collections.emptyList()
        : Arrays.asList(participants.split(","));
  }
}