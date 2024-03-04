package com.tamnguyen.serviceaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission {
  private Long id;
  private String name;
}
