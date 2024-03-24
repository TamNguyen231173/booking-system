package com.tamnguyen.servicebooking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.servicebooking.DTOs.ResponseSuccess;
import com.tamnguyen.servicebooking.DTOs.Bookings.UpdateBooking;
import com.tamnguyen.servicebooking.models.Booking;
import com.tamnguyen.servicebooking.services.BookingService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
  private final BookingService bookingService;

 @GetMapping("/get-all")
  public ResponseEntity<ResponseSuccess> getAll() {
    List<Booking> bookings = bookingService.getBookings();

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Get all bookings")
        .data(bookings)
        .build());
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<ResponseSuccess> getBooking(@PathVariable String bookingId) {
    Booking booking = bookingService.getBooking(bookingId);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Booking found")
        .data(booking)
        .build());
  }

  @PatchMapping("/{bookingId}")
  public ResponseEntity<ResponseSuccess> updateBooking(@PathVariable String bookingId, @RequestBody UpdateBooking body) {
    Booking booking = bookingService.updateBooking(bookingId, body);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Booking updated")
        .data(booking)
        .build());
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<ResponseSuccess> deleteBooking(@PathVariable String bookingId) {
    bookingService.deleteBooking(bookingId);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Booking deleted")
        .build());
  }
  
}
