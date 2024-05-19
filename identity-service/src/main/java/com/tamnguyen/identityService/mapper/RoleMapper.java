package com.tamnguyen.identityService.mapper;

import com.tamnguyen.identityService.dto.request.RoleRequest;
import com.tamnguyen.identityService.dto.response.RoleResponse;
import com.tamnguyen.identityService.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // ignore mapping permissions
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}