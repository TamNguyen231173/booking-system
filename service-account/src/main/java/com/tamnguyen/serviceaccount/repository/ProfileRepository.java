package com.tamnguyen.serviceaccount.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tamnguyen.serviceaccount.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
  Profile findByAccountId(Long accountId);
} 
