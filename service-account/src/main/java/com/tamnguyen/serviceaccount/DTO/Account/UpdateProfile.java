package com.tamnguyen.serviceaccount.DTO.Account;

import java.time.LocalDateTime;

import com.tamnguyen.serviceaccount.enums.ContactMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfile {
  private String fullName;
  private String email;
  private String address;
  private String phone;
  private String idCard;
  private LocalDateTime dob;
  private ContactMethod contactMethod;
}
