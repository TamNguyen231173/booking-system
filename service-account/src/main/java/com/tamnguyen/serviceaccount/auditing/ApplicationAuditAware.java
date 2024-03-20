package com.tamnguyen.serviceaccount.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tamnguyen.serviceaccount.model.Account;

public class ApplicationAuditAware implements AuditorAware<Integer> {
  @Override
  public Optional<Integer> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    Account AccountPrincipal = (Account) authentication.getPrincipal();

    return Optional.ofNullable(AccountPrincipal.getId()).map(Long::intValue);
  }
}