package com.tamnguyen.serviceaccount.model.Token;

import com.tamnguyen.serviceaccount.enums.Token.TokenType;
import com.tamnguyen.serviceaccount.model.Account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(of = { "id" })
@Table(name = "tokens")
public class Token {
  @Id
  @GeneratedValue
  public Integer id;

  @Column(unique = true)
  public String token;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  public TokenType type = TokenType.REFRESH_TOKEN;

  public boolean expired;

  public boolean revoked;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  public Account account;
}
