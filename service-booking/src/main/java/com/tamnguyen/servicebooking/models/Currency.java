
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
@Document(collection = "currencies")
public class Currency {

  @Id
  private String id;

  @NotNull
  @Indexed(unique = true)
  private String name;

  @NotNull
  @Indexed(unique = true)
  private String code;

  @NotNull
  private Double exchangeRate;

  @Builder.Default
  private Boolean isDeleted = false;
}
