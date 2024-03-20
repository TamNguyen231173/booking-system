package com.tamnguyen.serviceaccount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.serviceaccount.DTO.Auth.AuthRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.AuthResponse;
import com.tamnguyen.serviceaccount.DTO.Auth.RegisterRequest;
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
      .message("User registered successfully")
      .data(acc)
      .build());
  }

  @DeleteMapping("/logout") 
  public ResponseEntity<ResponseSuccess> logout(@RequestBody String refreshToken) {
    authService.logout(refreshToken);
    return ResponseEntity.ok(ResponseSuccess.builder()
      .status("success")
      .message("User logged out successfully")
      .build());
  }
}
