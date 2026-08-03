package com.hritik.portfolio.entity;

import com.hritik.portfolio.enums.OtpTargetType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OtpVerification extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String target; // Email or Mobile number

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpTargetType targetType;

    @Column(name = "otp_hash", nullable = false)
    private String otpHash;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private int attempts = 0;
}
