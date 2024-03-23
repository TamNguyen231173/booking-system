package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
  PENDING("PENDING"),
  COMPLETED("COMPLETED"),
  FAILED("FAILED"),
  REFUNDED("REFUNDED");

  private final String value;
}
