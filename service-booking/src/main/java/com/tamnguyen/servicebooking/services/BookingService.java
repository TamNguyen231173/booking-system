package com.tamnguyen.servicebooking.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.DTOs.Bookings.UpdateBooking;
import com.tamnguyen.servicebooking.models.Booking;
import com.tamnguyen.servicebooking.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
  private final BookingRepository bookingRepository;

  public List<Booking> getBookings() {
    return bookingRepository.findAll();
  }
  
  public Booking getBooking(String bookingId) {
    return bookingRepository.findById(bookingId)
    .orElseThrow(() -> new RuntimeException("Booking not found"));
  }

  public Booking updateBooking(String bookingId,UpdateBooking body) {
    Booking booking =  bookingRepository.findById(bookingId)
      .orElseThrow(() -> new RuntimeException("Booking not found"));
  
    BeanUtils.copyProperties(body, booking, "id", "createdDate", "updatedDate", "isDeleted");

    bookingRepository.save(booking);

    return booking;
  }

  public void deleteBooking(String bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
      .orElseThrow(() -> new RuntimeException("Booking not found"));

    booking.setIsDeleted(true);

    bookingRepository.save(booking);
  }
}
