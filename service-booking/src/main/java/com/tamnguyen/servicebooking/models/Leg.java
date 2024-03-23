package com.tamnguyen.servicebooking.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tamnguyen.servicebooking.enums.LegStatus;
import com.tamnguyen.servicebooking.validators.CheckDates;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@CheckDates
@Document(collection = "legs")
public class Leg {
  @Id
  private String id;

  @DBRef
  private Flight flight;

  @DBRef
  private Airport departureAirport;

  @DBRef
  private Airport arrivalAirport;

  @NotNull
  private LocalDateTime departureTime;

  @NotNull
  private LocalDateTime arrivalTime;

  @Builder.Default
  private LegStatus status = LegStatus.SCHEDULED;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime updatedDate;

  @Builder.Default
  @NotNull
  private Boolean isDeleted = false;
}
