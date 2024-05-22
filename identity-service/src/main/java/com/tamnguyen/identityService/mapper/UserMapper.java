package com.tamnguyen.identityService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tamnguyen.identityService.dto.request.UserCreationRequest;
import com.tamnguyen.identityService.dto.request.UserUpdateRequest;
import com.tamnguyen.identityService.dto.response.UserResponse;
import com.tamnguyen.identityService.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
