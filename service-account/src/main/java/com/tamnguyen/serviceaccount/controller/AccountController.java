package com.tamnguyen.serviceaccount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.serviceaccount.DTO.ResponseSuccess;
import com.tamnguyen.serviceaccount.DTO.Account.ResponseAccount;
import com.tamnguyen.serviceaccount.DTO.Account.UpdateAccountRequest;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private ResponseAccount getAuthenticatedAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseAccount.fromAccount((Account) auth.getPrincipal());
    }

    @GetMapping("/")
    public ResponseEntity<ResponseSuccess> getAccount() {
        ResponseAccount account = getAuthenticatedAccount();

        var data = accountService.getAccountById(account.getId());
        
        return ResponseEntity.ok(
            ResponseSuccess.builder()
            .status("success")
            .message("Account found")
            .data(data)
            .build());
    }

    @PatchMapping("/")
    public ResponseEntity<ResponseSuccess> updateAccount(@RequestBody UpdateAccountRequest body) {
        try {
            ResponseAccount account = getAuthenticatedAccount();
            var data = accountService.updateAccount(account.getId(), body);
            
            return ResponseEntity.ok(
                ResponseSuccess.builder()
                .status("success")
                .message("Account updated")
                .data(data)
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<ResponseSuccess> deleteAccount() {
        try {
            ResponseAccount account = getAuthenticatedAccount();
            accountService.deactivateAccount(account.getId());
            
            return ResponseEntity.ok(
                ResponseSuccess.builder()
                .status("success")
                .message("Account deleted")
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
