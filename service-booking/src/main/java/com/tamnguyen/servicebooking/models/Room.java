package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

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
public class Room {
  @Id
  private String id;

  @NotNull
  @Indexed(unique = true)
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

  @DBRef
  private Vender vender;

  @Builder.Default
  private Boolean isDeleted = false;
}
