package com.hritik.portfolio.repository;

import com.hritik.portfolio.entity.OtpVerification;
import com.hritik.portfolio.enums.OtpTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification,Long> {
    Optional<OtpVerification> findByTargetAndTargetType(String target, OtpTargetType targetType);
    @Modifying
    @Query("DELETE FROM OtpVerification o WHERE o.expiryTime < :now")
    int deleteExpiredOtps(@Param("now") LocalDateTime now);
}
