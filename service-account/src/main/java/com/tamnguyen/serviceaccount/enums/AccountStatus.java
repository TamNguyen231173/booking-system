package com.tamnguyen.serviceaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE");

  private final String status;
}