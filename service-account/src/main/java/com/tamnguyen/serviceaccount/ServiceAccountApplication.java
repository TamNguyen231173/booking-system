package com.tamnguyen.serviceaccount;

import static com.tamnguyen.serviceaccount.enums.Role.ADMIN;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tamnguyen.serviceaccount.model.Account;
import com.tamnguyen.serviceaccount.service.AccountService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableDiscoveryClient
public class ServiceAccountApplication {
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;

	@Value("${admin.username}")
	private String adminUsername;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.password}")
	private String adminPassword;
	
	public ServiceAccountApplication(PasswordEncoder passwordEncoder, AccountService accountService) {
		this.passwordEncoder = passwordEncoder;
		this.accountService = accountService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceAccountApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
	  return args -> {
			var admin = accountService.getAccountByName(adminUsername);
			if (admin.isEmpty()) {
					var account = Account.builder()
									.username(adminUsername)
									.email(adminEmail)
									.password(passwordEncoder.encode(adminPassword))
									.role(ADMIN)
									.build();
					try {
							accountService.createAccount(account);
					} catch (Exception e) {
							// System.err.println("Failed to create admin account: " + e.getMessage());
					}
			};
		};
	}
}
