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
import com.tamnguyen.serviceaccount.DTO.Account.UpdateProfile;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.model.Profile;
import com.tamnguyen.serviceaccount.service.AccountService;
import com.tamnguyen.serviceaccount.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ProfileService profileService;

    private Account getAuthenticatedAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Account) auth.getPrincipal();
    }

    @GetMapping("/")
    public ResponseEntity<ResponseSuccess> getAccount() {
        Account account = getAuthenticatedAccount();

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
            Account account = getAuthenticatedAccount();
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
            Account account = getAuthenticatedAccount();
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

    @PatchMapping("/update-profile")
    public ResponseEntity<ResponseSuccess> updateProfile(@RequestBody UpdateProfile request) {
        Account account = getAuthenticatedAccount();
        Profile profile = profileService.findOneAndUpdate(account, request);

        return ResponseEntity.ok(
            ResponseSuccess.builder()
            .status("success")
            .message("Updated profile")
            .data(profile)
            .build());
    }
}
