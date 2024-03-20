package com.tamnguyen.serviceaccount.DTO.Account;

import com.tamnguyen.serviceaccount.enums.Role;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.model.Profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAccount {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Profile profile;

    public static ResponseAccount fromAccount(Account account) {
        return ResponseAccount.builder()
            .id(account.getId())
            .username(account.getUsername())
            .email(account.getEmail())
            .role(account.getRole())
            .profile(account.getProfile())
            .build();
    }
}
