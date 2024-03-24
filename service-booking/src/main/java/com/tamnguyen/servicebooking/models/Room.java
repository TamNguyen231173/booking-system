package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tamnguyen.servicebooking.enums.RoomStatus;
import com.tamnguyen.servicebooking.enums.RoomType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rooms")
public class Room {
  @Id
  private String id;

  @NotNull
  private String name;

  @NotNull
  private RoomType type;

  @NotNull
  private Double pricePerNight;

  @NotNull
  private Integer bedCount;

  @NotNull
  private Integer maxGuests;

  @Builder.Default
  private RoomStatus status = RoomStatus.AVAILABLE;

  @NotNull
  @DBRef
  private Vender vender;

  @Builder.Default
  private Boolean isDeleted = false;
}
