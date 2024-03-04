package com.tamnguyen.serviceaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {
  private Long id;
  private Long userId;
  private Long roleId;
}