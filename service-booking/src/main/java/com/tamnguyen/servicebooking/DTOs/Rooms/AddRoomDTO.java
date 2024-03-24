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
public class AddRoomDTO {
  private String id_vender;
  private String name;
  private RoomType type;
  private Double pricePerNight;
  private Integer bedCount;
  private Integer maxGuests;
  private RoomStatus status;
}
