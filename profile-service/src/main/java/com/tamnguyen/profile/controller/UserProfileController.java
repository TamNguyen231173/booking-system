package com.tamnguyen.profile.controller;

import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);
    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getUserProfile(profileId);
    }
}
