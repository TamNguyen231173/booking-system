package com.tamnguyen.serviceaccount.model.Account;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.tamnguyen.serviceaccount.enums.Account.ContactMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
@EqualsAndHashCode(of = { "id" })
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "dob", nullable = false)
  private LocalDateTime dob;

  @Column(name = "id_card", nullable = false, unique = true)
  private String idCard;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "preferred_contact_method", nullable = false)
  private ContactMethod preferredContactMethod = ContactMethod.EMAIL;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
