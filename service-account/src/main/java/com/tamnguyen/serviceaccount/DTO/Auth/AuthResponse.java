package com.tamnguyen.serviceaccount.DTO.Auth;

import com.tamnguyen.serviceaccount.DTO.Account.ResponseAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  private ResponseAccount account;
  private String accessToken;
  private String refreshToken;
}