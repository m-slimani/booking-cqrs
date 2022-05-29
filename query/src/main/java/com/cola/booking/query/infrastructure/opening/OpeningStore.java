package com.cola.booking.query.infrastructure.opening;

import com.cola.booking.query.domain.opening.Opening;
import java.time.LocalDate;
import java.util.List;

public interface OpeningStore {

  List<Opening> getOpenings(LocalDate date);
}
