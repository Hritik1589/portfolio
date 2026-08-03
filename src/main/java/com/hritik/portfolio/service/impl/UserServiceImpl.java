package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.UserProfileUpdateRequest;
import com.hritik.portfolio.dto.response.UserProfileResponse;
import com.hritik.portfolio.dto.response.UserResponse;
import com.hritik.portfolio.entity.Role;
import com.hritik.portfolio.entity.User;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.UserMapper;
import com.hritik.portfolio.repository.UserRepository;
import com.hritik.portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserProfileResponse getMyProfile() {
        User user = getAuthenticatedUser();
        return mapToProfileResponse(user);
    }

    @Override
    public UserProfileResponse updateMyProfile(UserProfileUpdateRequest request) {
        User user = getAuthenticatedUser();

        // Update allowed fields
        user.setName(request.getName());
        user.setMobile(request.getMobile());

        user = userRepository.save(user);
        return mapToProfileResponse(user);
    }

    // --- Private Helper Methods ---

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserProfileResponse mapToProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .isEmailVerified(user.isActive())
                // 🚨 FIXED: Convert Enum to String by calling .name()
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()))
                .build();
    }
}
