package com.tamnguyen.serviceaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PasswordResetStatus {
  PENDING("PENDING"),
  COMPLETED("COMPLETED"),
  EXPIRED("EXPIRED");

  private final String status;
}
