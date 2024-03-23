package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {

  @Id
  private String id;

  @NotNull
  @Size(min = 2, max = 100)
  private String name;

  @NotNull
  @Email
  @Indexed(unique = true)
  private String email;

  @NotNull
  @Size(min = 10, max = 15)
  @Indexed(unique = true)
  private String phone;

  @NotNull
  private String address;

  @Builder.Default
  private Boolean isDeleted = false;
}
