package com.hritik.portfolio.strategy.otp;

import com.hritik.portfolio.enums.OtpTargetType;

public interface OtpProvider {
    /**
     * @param target The email address or mobile number.
     * @param otp The 6-digit one time password.
     */
    void sendOtp(String target, String otp);

    /**
     * @return The target type this provider handles.
     */
    OtpTargetType getTargetType();
}
