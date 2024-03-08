package com.tamnguyen.serviceaccount.controller.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamnguyen.serviceaccount.model.Account.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  @JsonProperty("account")
  private Account account;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;
}