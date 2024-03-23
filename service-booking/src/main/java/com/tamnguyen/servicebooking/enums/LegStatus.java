package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LegStatus {
  SCHEDULED("SCHEDULED"),
  DEPARTED("DEPARTED"),
  IN_FLIGHT("IN_FLIGHT"),
  ARRIVED("ARRIVED"),
  CANCELLED("CANCELLED");

  private final String value;
}
