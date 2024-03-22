package com.tamnguyen.serviceaccount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.serviceaccount.DTO.Auth.AuthRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.AuthResponse;
import com.tamnguyen.serviceaccount.DTO.Auth.EmailRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.RefreshTokenDTO;
import com.tamnguyen.serviceaccount.DTO.Auth.RegisterRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.ResetPasswordRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.TokenRequest;
import com.tamnguyen.serviceaccount.model.PasswordReset;
import com.tamnguyen.serviceaccount.DTO.ResponseSuccess;
import com.tamnguyen.serviceaccount.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirements
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseSuccess> postMethodName(@RequestBody RegisterRequest request) {
    var acc = authService.register(request);

    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Email sent to " + acc.getEmail() + " for verification")
      .build());
  }

  @DeleteMapping("/logout") 
  public ResponseEntity<ResponseSuccess> logout(@RequestBody RefreshTokenDTO request) {
    authService.logout(request.getToken());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("User logged out successfully")
      .build());
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenDTO request) {
    return ResponseEntity.ok(authService.refreshToken(request.getToken()));
  }

  @PostMapping("/verify-email")
  public ResponseEntity<ResponseSuccess> verifyEmail(@RequestBody TokenRequest request) {
    authService.verifyEmail(request.getCode());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Email verified successfully")
      .build());
  }

  @PostMapping("/resend-email")
  public ResponseEntity<ResponseSuccess> resendEmail(@RequestBody EmailRequest request) {
    authService.resendVerifyCode(request.getEmail());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Email sent successfully")
      .build());
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ResponseSuccess> forgotPassword(@RequestBody EmailRequest request) {
    authService.forgotPassword(request.getEmail());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Email sent successfully")
      .build());
  }

  @PostMapping("/verify-reset-password")
  public ResponseEntity<ResponseSuccess> verifyResetPassword(@RequestBody TokenRequest request) {
    var token = authService.verifyCodePasswordReset(request.getCode());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Token verified successfully")
      .data(token)
      .build());
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ResponseSuccess> resetPassword(@RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request.getToken(), request.getOldPassword(), request.getNewPassword());
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("Password reset successfully")
      .build());
  }
}
