package com.tamnguyen.identityService.repository.httpclient;

import com.tamnguyen.identityService.configuration.AuthenticationRequestIntercepter;
import com.tamnguyen.identityService.dto.request.profile.ProfileCreationRequest;
import com.tamnguyen.identityService.dto.response.ApiResponse;
import com.tamnguyen.identityService.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile.url}",
        configuration = AuthenticationRequestIntercepter.class)
public interface ProfileClient {
    @PostMapping(value = "/internal/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
