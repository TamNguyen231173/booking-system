package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
  FLIGHT("Flight"),
  HOTEL("Hotel");

  private final String value;
}
