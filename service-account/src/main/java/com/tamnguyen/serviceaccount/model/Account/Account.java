package com.tamnguyen.serviceaccount.model.Account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tamnguyen.serviceaccount.enums.Account.AccountStatus;
import com.tamnguyen.serviceaccount.model.Token.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(unique = true)
  private String email;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AccountStatus status = AccountStatus.ACTIVE;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "account")
  private List<Token> tokens;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public boolean isAccountNonExpired() {
    // Return true if the account is not expired.
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // Return true if the account is not locked.
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // Return true if the credentials are not expired.
    return true;
  }

  @Override
  public boolean isEnabled() {
    // Return true if the account is enabled.
    return status == AccountStatus.ACTIVE;
  }
}
