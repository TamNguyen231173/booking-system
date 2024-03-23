package com.tamnguyen.serviceaccount.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/* 
 * This class is used to store the login attempts.
 * Count number of login attempts.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login_attempts")
@EqualsAndHashCode(of = { "id" })
public class LoginAttempt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ip_address", nullable = false)
  private String ipAddress;

  @Column(name = "time", nullable = false, updatable = false)
  private LocalDateTime time;

  @Column(name = "attempt_count", nullable = false)
  private Integer attemptCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @PrePersist
  protected void onCreate() {
    this.time = LocalDateTime.now();
    this.attemptCount = 1;
  }

  @PreUpdate
  protected void onUpdate() {
    this.attemptCount++;
  }
}