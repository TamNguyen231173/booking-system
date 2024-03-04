package com.tamnguyen.serviceaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String phone;
  private String status;
  private String createdAt;
  private String updatedAt;

  public User() {
  }
}
