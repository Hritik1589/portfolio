package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.UserProfileUpdateRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.UserProfileResponse;
import com.hritik.portfolio.dto.response.UserResponse;
import com.hritik.portfolio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing user profiles")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {
        UserProfileResponse profile = userService.getMyProfile();
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .success(true)
                        .message("Profile fetched successfully")
                        .data(profile)
                        .build()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyProfile(
            @Valid @RequestBody UserProfileUpdateRequest request) {
        UserProfileResponse updatedProfile = userService.updateMyProfile(request);
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .success(true)
                        .message("Profile updated successfully")
                        .data(updatedProfile)
                        .build()
        );
    }
}
