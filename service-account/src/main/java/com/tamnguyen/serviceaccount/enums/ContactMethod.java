package com.tamnguyen.serviceaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactMethod {
  EMAIL("Email"),
  PHONE("Phone");

  private final String value;
}
