package com.tamnguyen.identityService.controller;

import com.tamnguyen.identityService.dto.request.UserCreationRequest;
import com.tamnguyen.identityService.dto.response.ApiResponse;
import com.tamnguyen.identityService.dto.response.UserResponse;
import com.tamnguyen.identityService.service.UserService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/create")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
       return ApiResponse.<UserResponse>builder()
               .result(userService.createUser(request))
               .build();
    }
}
