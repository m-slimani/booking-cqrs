package com.cola.booking.query.infrastructure.history;

import com.cola.booking.query.domain.BookingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingHistoryEntityMapper {

  BookingHistoryEntityMapper BOOKING_HISTORY_ENTITY_MAPPER =
      Mappers.getMapper(BookingHistoryEntityMapper.class);

  BookingHistory fromEntity(BookingHistoryEntity bookingHistoryEntity);

  BookingHistoryEntity toEntity(BookingHistory bookingHistory);
}
