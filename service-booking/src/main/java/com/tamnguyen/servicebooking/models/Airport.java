package com.tamnguyen.servicebooking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airports")
public class Airport {
  
  @Id
  private String id;

  @NotNull
  @Indexed(unique = true)
  private String code;

  @NotNull
  private String name;
  
  @NotNull
  private String city;
  
  @NotNull
  private String country;

  @Builder.Default
  @NotNull
  private Boolean isDeleted = false;
}
