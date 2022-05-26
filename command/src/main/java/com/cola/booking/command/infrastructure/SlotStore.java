package com.cola.booking.command.infrastructure;

public interface SlotStore {

  void freeUp(Long slotNumber);
}
