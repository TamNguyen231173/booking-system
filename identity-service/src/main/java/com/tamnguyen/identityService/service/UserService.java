package com.tamnguyen.identityService.service;

import com.tamnguyen.identityService.constant.PredefinedRole;
import com.tamnguyen.identityService.dto.request.auth.PasswordCreationRequest;
import com.tamnguyen.identityService.dto.request.user.UserCreationRequest;
import com.tamnguyen.identityService.dto.request.user.UserUpdateRequest;
import com.tamnguyen.identityService.dto.response.UserResponse;
import com.tamnguyen.identityService.entity.Role;
import com.tamnguyen.identityService.entity.User;
import com.tamnguyen.identityService.exception.AppException;
import com.tamnguyen.identityService.exception.ErrorCode;
import com.tamnguyen.identityService.mapper.ProfileMapper;
import com.tamnguyen.identityService.mapper.UserMapper;
import com.tamnguyen.identityService.repository.RoleRepository;
import com.tamnguyen.identityService.repository.UserRepository;
import com.tamnguyen.identityService.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        //        Optional<Role> role = roleRepository.findById(PredefinedRole.USER_ROLE);
        //        if (role.isPresent()) {
        //            roles.add(role.get());
        //        }
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());
        profileClient.createProfile(profileRequest);

        kafkaTemplate.send("user-created", user.getId());

        return userMapper.toUserResponse(user);
    }

    public void createPassword(PasswordCreationRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (StringUtils.hasText(user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_EXISTED);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var userResponse = userMapper.toUserResponse(user);

        userResponse.setNoPassword(!StringUtils.hasText(user.getPassword()));

        return userResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        //        List<User> users = userRepository.findAll();
        //
        //        List<UserResponse> userResponses = new ArrayList<>();
        //
        //        for (User user : users) {
        //            UserResponse userResponse = userMapper.toUserResponse(user);
        //            userResponses.add(userResponse);
        //        }
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}
