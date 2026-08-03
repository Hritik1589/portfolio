package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.UserProfileUpdateRequest;
import com.hritik.portfolio.dto.response.UserProfileResponse;
import com.hritik.portfolio.dto.response.UserResponse;

public interface UserService {
    UserProfileResponse getMyProfile();

    UserProfileResponse updateMyProfile(UserProfileUpdateRequest request);
}