package com.tamnguyen.serviceaccount.model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permission")
public class RolePermission {
  @Id
  private Long id;
  private Long roleId;
  private Long permissionId;
}