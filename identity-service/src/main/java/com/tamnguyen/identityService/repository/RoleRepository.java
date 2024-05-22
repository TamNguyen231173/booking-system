package com.tamnguyen.identityService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tamnguyen.identityService.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
