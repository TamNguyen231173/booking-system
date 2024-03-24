package com.tamnguyen.servicebooking.DTOs.Rooms;

import com.tamnguyen.servicebooking.enums.RoomStatus;
import com.tamnguyen.servicebooking.enums.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomDTO {
  private String name;
  private RoomType type;
  private RoomStatus status;
  private Double pricePerNight;
  private Integer bedCount;
  private Integer maxGuests;
  private Boolean isDeleted;
}
