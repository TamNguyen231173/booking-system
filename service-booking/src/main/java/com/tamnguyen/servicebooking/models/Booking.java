package com.tamnguyen.servicebooking.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "bookings")
@CheckDates
public class Booking {
  
  @Id
  private String id;

  @NotNull
  private LocalDateTime bookingDate;

  @NotNull
  private LocalDateTime checkInDate;

  @NotNull
  private LocalDateTime checkOutDate;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime updatedDate;

  @NotNull
  private String status;

  @Builder.Default
  @NotNull
  private Boolean isDeleted = false;
}
