package com.tamnguyen.serviceaccount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/welcome")
@SecurityRequirements
@RequiredArgsConstructor
public class WelcomeController {
  @GetMapping("/")
  public ResponseEntity<String> welcome() {
    return ResponseEntity.ok("Welcome to booking system...");
  }
}
