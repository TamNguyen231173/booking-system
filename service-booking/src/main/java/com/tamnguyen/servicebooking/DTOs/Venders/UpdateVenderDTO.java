package com.tamnguyen.servicebooking.DTOs.Venders;

import com.tamnguyen.servicebooking.enums.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVenderDTO {
  private String name;
  private String address;
  private ServiceType service;
}
