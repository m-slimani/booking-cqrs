package com.cola.booking.command.application;

import com.cola.booking.command.application.model.BookingR;
import com.cola.booking.command.domain.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingRMapper {

  BookingRMapper BOOKING_R_MAPPER = Mappers.getMapper(BookingRMapper.class);

  @Mapping(source = "userId", target = "userId")
  @Mapping(target = "status", constant = "booked")
  @Mapping(source = "roomNumber", target = "roomNumber")
  Booking bookingRToBooking(BookingR bookingR);

  BookingR bookingToBookingR(Booking booking);

}