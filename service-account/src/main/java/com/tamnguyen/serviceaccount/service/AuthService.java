package com.tamnguyen.serviceaccount.service;

import static com.tamnguyen.serviceaccount.enums.Role.USER;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.DTO.EmailDetails;
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
  private final EmailService emailService;
  private final Random random = new Random();
  private final String emailTemplate = "src/main/resources/templates/verify-email.html";

  @SuppressWarnings("null")
  public ResponseAccount register(RegisterRequest request) {
   try {
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

    var code = getCode();
    
    sendEmail(account.getEmail(), code);

    saveUserToken(account, code, TokenType.VERIFY_CODE);

    return ResponseAccount.fromAccount(account);
   } catch(Exception e) {
      throw new RuntimeException("Error: " + e.getMessage());
   }
  }

  public AuthResponse login(AuthRequest request) {
    var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    authenticationManager.authenticate(auth);

    var acc = accountRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!acc.isVerify()) {
      throw new RuntimeException("Email not verified");
    }

    var jwtToken = jwtService.generateToken(acc);
    var refreshToken = jwtService.generateRefreshToken(acc);
    
    revokeAllUserTokens(acc);

    saveUserToken(acc, refreshToken, TokenType.REFRESH_TOKEN);

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

  public String verifyEmail(String code) {
    var account = verifyToken(code, TokenType.VERIFY_CODE);

    if (account == null) {
      return "Invalid code";
    }

    if (account.isVerify()) {
      return "Email already verified";
    }

    account.setVerify(true);
    accountRepository.save(account);

    return "Email verified successfully";
  }

  public String resendVerifyCode(String email) {
    var account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (account.isVerify()) {
      throw new RuntimeException("Email already verified");
    }

    var code = getCode();
    sendEmail(account.getEmail(), code);
    saveUserToken(account, code, TokenType.VERIFY_CODE);

    return "Verification code sent to your email";
  }

  public String verifyCodePasswordReset(String token) {
    var oldToken = tokenRepository.findByTokenAndType(token, TokenType.VERIFY_CODE)
        .orElseThrow(() -> new RuntimeException("Invalid code"));

    if (oldToken.isExpired()) {
      throw new RuntimeException("Token is expired");
    }

    oldToken.setExpired(true);
    tokenRepository.save(oldToken);

    var newToken = jwtService.generateToken(oldToken.getAccount());

   saveUserToken(oldToken.getAccount(), newToken, TokenType.PASSWORD_RESET);

    return newToken;
  }

  public String resetPassword(String token, String oldPassword, String newPassword) {
    var account = verifyToken(token, TokenType.PASSWORD_RESET);

    if (account == null) {
      return "Invalid code";
    }

    if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
      return "Invalid old password";
    }

    account.setPassword(passwordEncoder.encode(newPassword));
    accountRepository.save(account);

    return "Password reset successfully";
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
    
    saveUserToken(account, newRefreshToken, TokenType.REFRESH_TOKEN);

    return AuthResponse.builder()
        .account(ResponseAccount.fromAccount(account))
        .accessToken(jwtToken)
        .refreshToken(newRefreshToken)
        .build();
  }

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
  private void saveUserToken(Account account, String jwtToken, TokenType type) {
    Token token = Token.builder()
        .account(account)
        .token(jwtToken)
        .type(type)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  
  public String forgotPassword(String email) {
    var account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    var code = getCode();
    sendEmail(account.getEmail(), code);
    
    saveUserToken(account, code, TokenType.VERIFY_CODE);

    return "Reset password link sent to your email";
  }

  Account verifyToken(String token, TokenType type) {
    var oldToken = tokenRepository.findByTokenAndType(token, type)
        .orElseThrow(() -> new RuntimeException("Invalid code"));

    if (oldToken.isExpired()) {
        throw new RuntimeException("Token is expired");
    }

    oldToken.setExpired(true);
    tokenRepository.save(oldToken);

    return oldToken.getAccount();
}

  String getCode() {
    int code = 100000 + this.random.nextInt(900000);
    return String.valueOf(code);
  }
  
  private void sendEmail(String email, String code) {
    try {
      var emailBody = Files.readString(Path.of(this.emailTemplate))
          .replace("{{title}}", "Email Verification")
          .replace("{{content}}", "Please use the code below to verify your email")
          .replace("{{code}}", code);

      var emailDetails = EmailDetails.builder()
          .recipient(email)
          .subject("Email Verification")
          .msgBody(emailBody)
          .build();

      emailService.sendEmail(emailDetails);
    } catch (Exception e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }
}
