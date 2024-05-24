package com.tamnguyen.profile.service;

import com.tamnguyen.profile.dto.request.ProfileCreationRequest;
import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.entity.UserProfile;
import com.tamnguyen.profile.mapper.UserProfileMapper;
import com.tamnguyen.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

   public UserProfileResponse creationProfile(ProfileCreationRequest request) {
       UserProfile userProfile = userProfileMapper.toUserProfile(request);
       userProfile = userProfileRepository.save(userProfile);

       return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getUserProfile(String id) {
       UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

       return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public List<UserProfileResponse> getAllProfiles() {
        var profiles = userProfileRepository.findAll();

        return profiles.stream().map(userProfileMapper::toUserProfileResponse).toList();
    }

    public void deleteUserProfile(String id) {
        userProfileRepository.deleteById(id);
    }

    public void deleteAll() {
        userProfileRepository.deleteAll();
    }
}
