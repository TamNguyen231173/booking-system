package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tamnguyen.servicebooking.enums.ServiceType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "venders")
public class Vender {
  @Id
  private String id;

  @NotNull
  private String name;

  @NotNull
  @Email
  @Indexed(unique = true)
  private String email;

  @NotNull
  private String phone;

  @NotNull
  private String address;

  @NotNull
  private ServiceType service;

  @Builder.Default
  private Boolean isDeleted = false;
}
