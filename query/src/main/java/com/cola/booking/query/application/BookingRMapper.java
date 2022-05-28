package com.cola.booking.query.application;

import com.cola.booking.query.application.model.BookingR;
import com.cola.booking.query.domain.BookingHistory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingRMapper {

  BookingRMapper BOOKING_R_MAPPER = Mappers.getMapper(BookingRMapper.class);

  @Mapping(source = "participants", target = "participants")
  BookingR toBookingR(BookingHistory bookingHistory);

  default List<String> mapParticipants(String participants) {
    return StringUtils.isNotBlank(participants)
        ? Arrays.asList(participants.split(","))
        : Collections.emptyList();
  }
}
