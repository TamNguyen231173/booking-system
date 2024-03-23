package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
  SCHEDULED("SCHEDULED"),
  ON_TIME("ON_TIME"),
  DELAYED("DELAYED"),
  DEPARTED("DEPARTED"),
  ARRIVED("ARRIVED"),
  CANCELLED("CANCELLED");

  private final String value;
}
