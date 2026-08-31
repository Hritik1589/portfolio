package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.LoginRequest;
import com.hritik.portfolio.dto.request.RegisterRequest;
import com.hritik.portfolio.dto.request.VerifyOtpRequest;
import com.hritik.portfolio.dto.response.JwtResponse;
import com.hritik.portfolio.entity.OtpVerification;
import com.hritik.portfolio.entity.RefreshToken;
import com.hritik.portfolio.entity.Role;
import com.hritik.portfolio.entity.User;
import com.hritik.portfolio.enums.OtpTargetType;
import com.hritik.portfolio.enums.RoleType;
import com.hritik.portfolio.exception.BadRequestException;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.repository.OtpVerificationRepository;
import com.hritik.portfolio.repository.RefreshTokenRepository;
import com.hritik.portfolio.repository.RoleRepository;
import com.hritik.portfolio.repository.UserRepository;
import com.hritik.portfolio.security.UserDetailsImpl;
import com.hritik.portfolio.security.jwt.JwtUtils;
import com.hritik.portfolio.service.AuthService;
import com.hritik.portfolio.strategy.otp.OtpProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OtpVerificationRepository otpRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final List<OtpProvider> otpProviders; // Dynamic abstraction

    @Value("${app.otp.expiration-minutes:10}")
    private int otpExpirationMinutes;

    @Value("${app.otp.max-attempts:3}")
    private int maxOtpAttempts;

    @Value("${app.jwt.refresh-expiration-ms}")
    private Long refreshTokenDurationMs;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found. Database seeded?"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .isActive(true) // 👈 Direct active kar diya, OTP ki zaroorat nahi
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyOtp(VerifyOtpRequest request) {
        OtpVerification otpEntity = otpRepository.findByTargetAndTargetType(request.getTarget(), request.getTargetType())
                .orElseThrow(() -> new ResourceNotFoundException("No OTP requested for this target"));

        if (otpEntity.getAttempts() >= maxOtpAttempts) {
            throw new BadRequestException("Maximum OTP attempts reached. Please request a new OTP.");
        }

        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP has expired. Please request a new one.");
        }

        if (!passwordEncoder.matches(request.getOtp(), otpEntity.getOtpHash())) {
            otpEntity.setAttempts(otpEntity.getAttempts() + 1);
            otpRepository.save(otpEntity);
            throw new BadRequestException("Invalid OTP.");
        }

        User user = userRepository.findByEmail(request.getTarget())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(true);
        userRepository.save(user);
        otpRepository.delete(otpEntity); // Clean up OTP after success
    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isActive()) {
            throw new BadRequestException("Account is inactive. Please verify your email.");
        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        // Handle refresh token
        // 1. Find the existing token (using the new method we just added)
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByUser(user);

        RefreshToken refreshToken;

        if (existingTokenOpt.isPresent()) {
            // 2. If token exists, just update it!
            refreshToken = existingTokenOpt.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        } else {
            // 3. If no token exists, build a new one
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                    .build();
        }

        // 4. Save to database
        refreshTokenRepository.save(refreshToken);

        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .id(userDetails.getId())
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public void resendOtp(String target) {
        generateAndSendOtp(target, OtpTargetType.EMAIL);
    }

    @Override
    @Transactional
    public void forgotPassword(String target) {
        User user = userRepository.findByEmail(target)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with this email"));
        generateAndSendOtp(user.getEmail(), OtpTargetType.EMAIL);
    }

    @Override
    @Transactional
    public void resetPassword(String target, String newPassword, String otp) {
        verifyOtp(new VerifyOtpRequest().setTarget(target).setOtp(otp).setTargetType(OtpTargetType.EMAIL));

        User user = userRepository.findByEmail(target).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void generateAndSendOtp(String target, OtpTargetType type) {
        // Cooldown check (Wait at least 1 minute before resending)
        otpRepository.findByTargetAndTargetType(target, type).ifPresent(existing -> {
            if (existing.getUpdatedAt() != null && existing.getUpdatedAt().plusMinutes(1).isAfter(LocalDateTime.now())) {
                throw new BadRequestException("Please wait 60 seconds before requesting a new OTP.");
            }
            otpRepository.delete(existing);
        });

        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification verification = OtpVerification.builder()
                .target(target)
                .targetType(type)
                .otpHash(passwordEncoder.encode(otp))
                .expiryTime(LocalDateTime.now().plusMinutes(otpExpirationMinutes))
                .attempts(0)
                .build();

        otpRepository.save(verification);

        // Find the right provider (Email vs Mobile abstraction)
        OtpProvider provider = otpProviders.stream()
                .filter(p -> p.getTargetType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No provider configured for type: " + type));

        provider.sendOtp(target, otp);
    }
}