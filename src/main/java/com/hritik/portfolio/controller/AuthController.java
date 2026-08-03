package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.*;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.JwtResponse;
import com.hritik.portfolio.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for Registration, OTP, Login, and Password Management")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user (Requires OTP verification next)")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful. Please check your email for the OTP.", null));
    }

    @Operation(summary = "Verify OTP to activate account")
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok(ApiResponse.success("Account verified successfully. You can now login.", null));
    }

    @Operation(summary = "Login and get JWT tokens")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));
    }

    @Operation(summary = "Resend OTP (respects 60-second cooldown)")
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Void>> resendOtp(@Valid @RequestBody TargetRequest request) {
        authService.resendOtp(request.getTarget());
        return ResponseEntity.ok(ApiResponse.success("OTP resent successfully.", null));
    }

    @Operation(summary = "Request a password reset OTP")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody TargetRequest request) {
        authService.forgotPassword(request.getTarget());
        return ResponseEntity.ok(ApiResponse.success("Password reset OTP sent to your email.", null));
    }

    @Operation(summary = "Reset password using OTP")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getTarget(), request.getNewPassword(), request.getOtp());
        return ResponseEntity.ok(ApiResponse.success("Password has been reset successfully.", null));
    }
}