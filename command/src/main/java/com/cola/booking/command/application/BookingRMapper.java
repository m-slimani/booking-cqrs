package com.cola.booking.command.application;

import com.cola.booking.command.application.model.BookingR;
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
public interface BookingRMapper {

  BookingRMapper BOOKING_R_MAPPER = Mappers.getMapper(BookingRMapper.class);

  @Mapping(target = "status", constant = "booked")
  @Mapping(source = "participants", target = "participants")
  Booking bookingRToBooking(BookingR bookingR);

  @Mapping(source = "participants", target = "participants")
  BookingR bookingToBookingR(Booking booking);

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