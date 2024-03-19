package com.tamnguyen.serviceaccount.enums.Account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountRole {
  ADMIN("ADMIN"),
  USER("USER");

  private final String role;

}