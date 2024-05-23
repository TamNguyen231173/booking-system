package com.tamnguyen.identityService.mapper;

import com.tamnguyen.identityService.dto.request.profile.ProfileCreationRequest;
import com.tamnguyen.identityService.dto.request.user.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
