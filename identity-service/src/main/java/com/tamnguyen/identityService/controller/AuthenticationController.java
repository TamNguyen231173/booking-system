package com.tamnguyen.identityService.controller;

import com.nimbusds.jose.JOSEException;
import com.tamnguyen.identityService.dto.request.auth.AuthenticationRequest;
import com.tamnguyen.identityService.dto.request.auth.IntrospectRequest;
import com.tamnguyen.identityService.dto.request.auth.LogoutRequest;
import com.tamnguyen.identityService.dto.request.auth.RefreshRequest;
import com.tamnguyen.identityService.dto.response.ApiResponse;
import com.tamnguyen.identityService.dto.response.AuthenticationResponse;
import com.tamnguyen.identityService.dto.response.IntrospectResponse;
import com.tamnguyen.identityService.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> authenticateOutbound(@RequestParam("code") String code) {
        var result = authenticationService.authenticateOutbound(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
