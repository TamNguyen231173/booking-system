package com.tamnguyen.profile.service;

import com.tamnguyen.profile.dto.identity.Credential;
import com.tamnguyen.profile.dto.identity.TokenExchangeParam;
import com.tamnguyen.profile.dto.identity.UserCreationParam;
import com.tamnguyen.profile.dto.request.ProfileCreationRequest;
import com.tamnguyen.profile.dto.response.UserProfileResponse;
import com.tamnguyen.profile.entity.UserProfile;
import com.tamnguyen.profile.exception.AppException;
import com.tamnguyen.profile.exception.ErrorCode;
import com.tamnguyen.profile.exception.ErrorNormalizer;
import com.tamnguyen.profile.mapper.UserProfileMapper;
import com.tamnguyen.profile.repository.IdentityClient;
import com.tamnguyen.profile.repository.UserProfileRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    private final IdentityClient identityClient;
    ErrorNormalizer errorNormalizer;

    @Value("${idp.clientId}")
    @NonFinal
    String clientId;
    @Value("${idp.clientSecret}")
    @NonFinal
    String clientSecret;
    @Value("${idp.scope}")
    @NonFinal
    String scope;
    @Value("${idp.grantType}")
    @NonFinal
    String grantType;

    public UserProfileResponse creationProfile(ProfileCreationRequest request) {
        try {
            // Create account in keyCloak
            // Exchange client token to get access token
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .grant_type(grantType)
                    .scope(scope)
                    .build());

            // Create user with client token
            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken()
                    , UserCreationParam.builder()
                            .username(request.getUsername())
                            .email(request.getEmail())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .enabled(true)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .value(request.getPassword())
                                    .temporary(false)
                                    .build()))
                            .build());
            String userId = extractUserId(creationResponse);

            UserProfile userProfile = userProfileMapper.toUserProfile(request);
            userProfile.setUserId(userId);
            userProfile = userProfileRepository.save(userProfile);

            return userProfileMapper.toUserProfileResponse(userProfile);
        } catch (FeignException e) {
            log.error("Error creating profile", e);
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    public String extractUserId(ResponseEntity<?> response) {
        List<String> locations = response.getHeaders().get("Location");
        if (locations == null || locations.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_CREATED);
        }
        return locations.stream()
                .filter(location -> location.contains("users"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not created"));
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
