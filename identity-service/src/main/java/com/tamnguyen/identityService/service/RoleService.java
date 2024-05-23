package com.tamnguyen.identityService.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tamnguyen.identityService.dto.request.user.RoleRequest;
import com.tamnguyen.identityService.dto.response.RoleResponse;
import com.tamnguyen.identityService.mapper.RoleMapper;
import com.tamnguyen.identityService.repository.PermissionRepository;
import com.tamnguyen.identityService.repository.RoleRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);

        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
