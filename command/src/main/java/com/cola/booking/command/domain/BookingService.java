package com.cola.booking.command.domain;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class BookingService {

  public void save(Booking booking) throws FunctionalException {
    if (Objects.isNull(booking)) {
      throw new FunctionalException("booking is mandatory");
    }
    if (Objects.isNull(booking.getOrganiserNumber())) {
      throw new FunctionalException("organiserNumber is mandatory");
    }
    if (StringUtils.isBlank(booking.getRoomNumber())) {
      throw new FunctionalException("roomNumber is mandatory");
    }
    if (Objects.isNull(booking.getSlotNumber())) {
      throw new FunctionalException("slotNumber is mandatory");
    }
  }
}
