package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BookingRooms")
public class BookingRoom {

  @Id
  private String id;

  @NotNull
  @Valid
  @DBRef
  private Booking booking;

  @NotNull
  @Valid
  @DBRef
  private Room room;

  @Builder.Default
  @NotNull
  private Boolean isDeleted = false;
}
