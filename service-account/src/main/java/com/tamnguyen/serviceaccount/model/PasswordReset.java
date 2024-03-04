package com.tamnguyen.serviceaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "password_reset")
public class PasswordReset {
  private Long id;
  private String token;
  private Long userId;
  private String status;
  private String createdAt;
  private String updatedAt;
}
