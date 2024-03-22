package com.tamnguyen.serviceaccount.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.DTO.Account.ResponseAccount;
import com.tamnguyen.serviceaccount.DTO.Account.UpdateAccountRequest;
import com.tamnguyen.serviceaccount.enums.AccountStatus;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        if (account.getId() != null && accountRepository.existsById(account.getId())) {
            throw new IllegalArgumentException("Account already exists");
        }
        return accountRepository.save(account);
    }

    public ResponseAccount updateAccount(Long id, UpdateAccountRequest body) {
        if (id == null || body == null) {
            throw new IllegalArgumentException("Account id and account must not be null");
        }
        System.out.println("id: " + id + " body: " + body);
        Account existingAccount = accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));

        BeanUtils.copyProperties(body, existingAccount, "id");

        System.out.println("existingAccount: " + existingAccount);

        accountRepository.save(existingAccount);

        return ResponseAccount.fromAccount(existingAccount);
    }

    public void deactivateAccount(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
    
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
    
        account.setStatus(AccountStatus.INACTIVE);
    
        accountRepository.save(account);
    }

    public Optional<Account> getAccountByName(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }
        Optional<Account> account = accountRepository.findByUsername(username);
        
        return account;
    }

    public ResponseAccount getAccountById(Long id) {
      if (id == null) {
        throw new IllegalArgumentException("Id must not be null");
      }

      var account = accountRepository.findByIdAndStatus(id, AccountStatus.ACTIVE);
      return ResponseAccount.fromAccount(account);
    }
}