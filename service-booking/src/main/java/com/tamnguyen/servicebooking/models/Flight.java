package com.tamnguyen.servicebooking.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tamnguyen.servicebooking.enums.FlightStatus;
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
@Document(collection = "flights")
public class Flight {
  
  @Id
  private String id;

  @NotNull
  @Indexed(unique = true)
  private String code;

  @NotNull
  private String departureAirport;

  @NotNull
  private String arrivalAirport;

  @NotNull
  private LocalDateTime departureTime;

  @NotNull
  private LocalDateTime arrivalTime;

  @NotNull
  private Double ticketPrice;

  @NotNull
  private String aircraftType;

  @NotNull
  private int availableSeats;

  @Builder.Default
  private FlightStatus status = FlightStatus.SCHEDULED;

  @DBRef
  private Vender vender;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime updatedDate;

  @Builder.Default
  private Boolean isDeleted = false;
}
