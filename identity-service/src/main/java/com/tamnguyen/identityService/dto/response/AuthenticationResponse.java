package com.tamnguyen.identityService.dto.response;

import com.tamnguyen.identityService.service.AuthenticationService;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    UserResponse user;
    TokenInfo accessToken;
    TokenInfo refreshToken;
}