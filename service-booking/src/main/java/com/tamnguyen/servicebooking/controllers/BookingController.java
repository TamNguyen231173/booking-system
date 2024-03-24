package com.tamnguyen.servicebooking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.servicebooking.services.BookingService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
  private final BookingService bookingService;

 @GetMapping("/{id}")
  public String getMethodName(@PathVariable String id) {
    return "1232";
  }
  
}
