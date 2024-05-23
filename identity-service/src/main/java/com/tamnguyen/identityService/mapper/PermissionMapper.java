package com.tamnguyen.identityService.mapper;

import org.mapstruct.Mapper;

import com.tamnguyen.identityService.dto.request.auth.PermissionRequest;
import com.tamnguyen.identityService.dto.response.PermissionResponse;
import com.tamnguyen.identityService.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
