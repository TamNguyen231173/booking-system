package com.tamnguyen.serviceaccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
  ACCESS_TOKEN("ACCESS_TOKEN"),
  REFRESH_TOKEN("REFRESH_TOKEN");

  private final String type;
}
