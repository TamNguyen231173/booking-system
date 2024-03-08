package com.tamnguyen.serviceaccount.model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "password_reset")
public class PasswordReset {
  @Id
  private Long id;
  private String token;
  private Long accountId;
  private String status;
  private String createdAt;
  private String updatedAt;
}
