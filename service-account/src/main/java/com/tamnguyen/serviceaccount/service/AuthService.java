package com.tamnguyen.serviceaccount.service;

import static com.tamnguyen.serviceaccount.enums.Role.USER;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.DTO.Account.ResponseAccount;
import com.tamnguyen.serviceaccount.DTO.Auth.AuthRequest;
import com.tamnguyen.serviceaccount.DTO.Auth.AuthResponse;
import com.tamnguyen.serviceaccount.DTO.Auth.RegisterRequest;
import com.tamnguyen.serviceaccount.enums.TokenType;
import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.model.Token;
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
  public ResponseAccount register(RegisterRequest request) {
    var usernameExists = accountRepository.existsByUsername(request.getUsername());
    var emailExists = accountRepository.existsByEmail(request.getEmail());

    if (usernameExists || emailExists) {
      throw new RuntimeException("Username or email already exists");
    }

    var account = Account.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(USER)
        .build();
      
    accountRepository.save(account);

    return ResponseAccount.fromAccount(account);
  }

  public AuthResponse login(AuthRequest request) {
    var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    authenticationManager.authenticate(auth);

    var acc = accountRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    var jwtToken = jwtService.generateToken(acc);
    var refreshToken = jwtService.generateRefreshToken(acc);
    
    revokeAllUserTokens(acc);

    saveUserToken(acc, refreshToken);

    return AuthResponse.builder()
        .account(ResponseAccount.fromAccount(acc))
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public String logout(String refreshToken) {
    var token = tokenRepository.findByToken(refreshToken)
        .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    token.setExpired(true);
    token.setRevoked(true);
    tokenRepository.save(token);

    return "Logout successfully";
  }

  public AuthResponse refreshToken(String refreshToken) {
    var token = tokenRepository.findByToken(refreshToken)
        .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    if (token.isExpired()) {
      return null;
    }

    var account = token.getAccount();
    var jwtToken = jwtService.generateToken(account);
    var newRefreshToken = jwtService.generateRefreshToken(account); // added semicolon here

    revokeAllUserTokens(account);
    
    saveUserToken(account, newRefreshToken);

    System.out.println("test=====" + jwtToken);

    return AuthResponse.builder()
        .account(ResponseAccount.fromAccount(account))
        .accessToken(jwtToken)
        .refreshToken(newRefreshToken)
        .build();
}

  // public String forgotPassword(String email) {
  //   var account = accountRepository.findByEmail(email)
  //       .orElseThrow(() -> new RuntimeException("User not found"));
  //   return "Reset password link sent to your email";
  // }

  private void revokeAllUserTokens(Account account) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getId());

    if (validUserTokens.isEmpty()) {
      return;
    }

    validUserTokens.forEach(token -> {
      token.setExpired(true);
    });

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
