package com.tamnguyen.servicebooking.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.DTOs.UpdateBooking;
import com.tamnguyen.servicebooking.models.Booking;
import com.tamnguyen.servicebooking.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
  private final BookingRepository bookingRepository;
  
  public Booking getBooking(String bookingId) {
    return bookingRepository.findById(bookingId)
    .orElseThrow(() -> new RuntimeException("Booking not found"));
  }

  public Booking updateBooking(String bookingId,UpdateBooking body) {
    var bookingUpdated =  bookingRepository.findById(bookingId)
    .map(booking -> {
        BeanUtils.copyProperties(body, booking, "id", "createdDate", "updatedDate", "isDeleted");
        return bookingRepository.save(booking);
    })
    .orElseThrow(() -> new RuntimeException("Booking not found"));

    return bookingUpdated;
  }

  public void deleteBooking(String bookingId) {
    bookingRepository.findById(bookingId)
    .map(booking -> {
        booking.setIsDeleted(true);
        return bookingRepository.save(booking);
    })
    .orElseThrow(() -> new RuntimeException("Booking not found"));
  }

  Booking createBooking(Booking booking) {
    return bookingRepository.save(booking);
  }
}
