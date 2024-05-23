package com.tamnguyen.profile.controller;

import com.tamnguyen.profile.dto.request.ProfileCreationRequest;
import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    private static final Logger log = LoggerFactory.getLogger(InternalUserProfileController.class);
    UserProfileService userProfileService;

    @PostMapping("/internal/create")
    UserProfileResponse createUserProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.creationProfile(request);
    }

    @DeleteMapping("/internal/")
    void deleteAll() {
        userProfileService.deleteAll();
    }

    @DeleteMapping("/internal/{profileId}")
    void deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteUserProfile(profileId);
    }
}
