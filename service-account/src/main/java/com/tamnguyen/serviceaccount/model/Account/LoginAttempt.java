package com.tamnguyen.serviceaccount.model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoginAttempt {
  @Id
  private Long id;
  private String ip_address;
  private String time;
  private Integer count;
}
