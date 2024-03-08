package com.tamnguyen.serviceaccount.model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
  @Id
  private Long id;
  private String fullName;
  private String address;
  private String phone;
  private String dob;
  private String idCard;
  private String avatar;
  private String createdAt;
  private String updatedAt;

}
