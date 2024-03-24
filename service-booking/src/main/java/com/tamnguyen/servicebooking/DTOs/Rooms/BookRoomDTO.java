package com.tamnguyen.servicebooking.DTOs.Rooms;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRoomDTO {
  private LocalDateTime checkIn;
  private LocalDateTime checkOut;
}
