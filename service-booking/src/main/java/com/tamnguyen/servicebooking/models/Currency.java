
package com.tamnguyen.servicebooking.models;

import java.math.BigDecimal;

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
  private String symbol;

  @NotNull
  private BigDecimal exchangeRate;

  @Builder.Default
  private Boolean isDeleted = false;
}
