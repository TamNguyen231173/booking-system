package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FlightStatus {
  PENDING("PENDING"),
  CONFIRMED("CONFIRMED"),
  DEPARTED("DEPARTED"),
  ARRIVED("ARRIVED"),
  CANCELLED("CANCELLED");

  private final String value;
}
