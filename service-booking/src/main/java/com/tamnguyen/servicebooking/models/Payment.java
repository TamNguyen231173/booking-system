package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
  @Id
  private String id;

  @NotNull
  @DBRef
  private Booking booking;

  @NotNull
  @DBRef
  private Currency currency;

  @NotNull
  private double amount;

  @NotNull
  private String paymentMethod;

  @Builder.Default
  @NotNull
  private Boolean isDeleted = false;
}
