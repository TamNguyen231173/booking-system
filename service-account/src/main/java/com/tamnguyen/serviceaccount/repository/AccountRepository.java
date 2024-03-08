package com.tamnguyen.serviceaccount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamnguyen.serviceaccount.model.Account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByUsername(String username);

  Optional<Account> findByEmail(String email);

  Account findByUsernameOrEmail(String username, String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
