package com.tamnguyen.servicebooking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomType {
  SINGLE("SINGLE"),
  DOUBLE("DOUBLE"),
  TRIPLE("TRIPLE"),
  QUAD("QUAD"), // A room with four beds.
  QUEEN("QUEEN"),
  KING("KING"), 
  TWIN("TWIN"), // A room with two single beds.
  STUDIO("STUDIO"), // A room with a bed and a kitchenette.
  SUITE("SUITE"); // A large room or a series of connected rooms at a hotel.

  private final String value;
}
