package com.tamnguyen.servicebooking.DTOs;

import java.time.LocalDateTime;

import com.tamnguyen.servicebooking.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBooking {
  private LocalDateTime checkInDate;
  private LocalDateTime checkOutDate;
  private BookingStatus status;
}
