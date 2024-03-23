package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FlightStatus {
  SCHEDULED("SCHEDULED"),
  DELAYED("DELAYED"),
  PENDING("PENDING"),
  DEPARTED("DEPARTED"),
  ARRIVED("ARRIVED"),
  CANCELLED("CANCELLED");

  private final String value;
}
