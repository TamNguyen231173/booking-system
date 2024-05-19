package com.tamnguyen.profile.mapper;

import com.tamnguyen.profile.dto.request.ProfileCreationRequest;
import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    @Mapping(source = "entity.id", target = "id")
    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
