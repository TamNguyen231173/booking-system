package com.tamnguyen.serviceaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
  VERIFY_CODE("VERIFY_CODE"),
  PASSWORD_RESET("PASSWORD_RESET"),
  ACCESS_TOKEN("ACCESS_TOKEN"),
  REFRESH_TOKEN("REFRESH_TOKEN");

  private final String type;
}
