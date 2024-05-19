package com.tamnguyen.profile.controller;

import com.tamnguyen.profile.dto.request.ProfileCreationRequest;
import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/")
    UserProfileResponse createUserProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.creationProfile(request);
    }

    @GetMapping("/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getUserProfile(profileId);
    }

    @DeleteMapping("/")
    void deleteAll() {
        userProfileService.deleteAll();
    }

    @DeleteMapping("/{profileId}")
    void deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteUserProfile(profileId);
    }
}
