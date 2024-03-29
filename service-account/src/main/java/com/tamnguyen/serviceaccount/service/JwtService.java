package com.tamnguyen.serviceaccount.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.model.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public boolean isTokenValid(String token, Object account) {
    final String email = extractEmail(token);
    return (email.equals(((Account) account).getEmail())) && !isTokenExpired(token);
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject); // Claims::getSubject is equal to c -> c.getSubject()
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(Object account) {
    return generateToken(new HashMap<>(), account);
  }

  public String generateToken(Map<String, Object> extraClaims, Object account) {
    return buildToken(extraClaims, account, jwtExpiration);
  }

  public String generateRefreshToken(Object account) {
    return buildToken(new HashMap<>(), account, refreshExpiration);
  }

  private String buildToken(Map<String, Object> extraClaims, Object account, long expiration) {
    var acc = (Account) account;
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(acc.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

   private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
