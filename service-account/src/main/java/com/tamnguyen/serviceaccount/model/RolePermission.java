package com.tamnguyen.serviceaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permission")
public class RolePermission {
  private Long id;
  private Long roleId;
  private Long permissionId;
}