package com.tamnguyen.identityService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tamnguyen.identityService.dto.request.user.RoleRequest;
import com.tamnguyen.identityService.dto.response.RoleResponse;
import com.tamnguyen.identityService.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // ignore mapping permissions
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
