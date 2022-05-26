package com.cola.booking.command.infrastructure;

import com.cola.booking.command.domain.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingStoreImpl implements BookingStore {


  @Override
  public Booking save(Booking booking) {
    return null;
  }

}
