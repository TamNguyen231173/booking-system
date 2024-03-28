package com.tamnguyen.gatewayservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain customSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    http
    .authorizeExchange(exchanges -> exchanges
      .pathMatchers("/api/v1/auth/**").permitAll()
      .pathMatchers("/api/v1/welcome/**").permitAll()
      .anyExchange().authenticated()
    )
    .csrf(csrf -> csrf.disable())
    .formLogin(formLogin -> formLogin.disable())
    .httpBasic(Customizer.withDefaults())
    .logout(logout -> logout.disable());
  return http.build();
  }
}
