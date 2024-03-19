package com.tamnguyen.serviceaccount.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.controller.Auth.AuthRequest;
import com.tamnguyen.serviceaccount.controller.Auth.AuthResponse;
import com.tamnguyen.serviceaccount.controller.Auth.RegisterRequest;
import com.tamnguyen.serviceaccount.enums.Token.TokenType;
import com.tamnguyen.serviceaccount.model.Account.Account;
import com.tamnguyen.serviceaccount.model.Token.Token;
import com.tamnguyen.serviceaccount.repository.AccountRepository;
import com.tamnguyen.serviceaccount.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AccountRepository accountRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @SuppressWarnings("null")
  public String register(RegisterRequest request) {
    var usernameExists = accountRepository.existsByUsername(request.getUsername());
    var emailExists = accountRepository.existsByEmail(request.getEmail());

    if (usernameExists || emailExists) {
      return "Username or email already exists";
    }

    var account = Account.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();

    accountRepository.save(account);

    return "Account created successfully";
  }

  public AuthResponse login(AuthRequest request) {
    var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    authenticationManager.authenticate(auth);

    var user = accountRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    revokeAllUserTokens(user);

    saveUserToken(user, jwtToken);
    return AuthResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void revokeAllUserTokens(Account account) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getId());

    if (validUserTokens.isEmpty()) {
      return;
    }

    validUserTokens.forEach(token -> { token.setExpired(true); });

    tokenRepository.saveAll(validUserTokens);
  }

  @SuppressWarnings("null")
  private void saveUserToken(Account account, String jwtToken) {
    var token = Token.builder()
        .account(account)
        .token(jwtToken)
        .type(TokenType.REFRESH_TOKEN)
        .expired(false)
        .build();
    tokenRepository.save(token);
  }
}
