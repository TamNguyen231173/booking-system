package com.tamnguyen.identityService.service;

import com.tamnguyen.identityService.dto.request.PermissionRequest;
import com.tamnguyen.identityService.dto.response.PermissionResponse;
import com.tamnguyen.identityService.entity.Permission;
import com.tamnguyen.identityService.mapper.PermissionMapper;
import com.tamnguyen.identityService.repository.PermissionRepository;

import java.util.List;

public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
