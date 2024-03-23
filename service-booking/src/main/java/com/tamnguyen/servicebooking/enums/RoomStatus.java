package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomStatus {
  AVAILABLE("AVAILABLE"),
  OCCUPIED("OCCUPIED"),
  UNDER_MAINTENANCE("UNDER_MAINTENANCE");

  private final String value;
}
