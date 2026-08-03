package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.LoginRequest;
import com.hritik.portfolio.dto.request.RegisterRequest;
import com.hritik.portfolio.dto.request.VerifyOtpRequest;
import com.hritik.portfolio.dto.response.JwtResponse;

public interface AuthService {
    void register(RegisterRequest request);
    void verifyOtp(VerifyOtpRequest request);
    JwtResponse login(LoginRequest request);
    void resendOtp(String target);
    void forgotPassword(String target);
    void resetPassword(String target, String newPassword, String otp);
}
