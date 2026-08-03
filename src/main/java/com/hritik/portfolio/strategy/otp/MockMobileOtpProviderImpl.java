package com.hritik.portfolio.strategy.otp;
import com.hritik.portfolio.enums.OtpTargetType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Mock provider to keep deployment free.
 * Can be replaced with Twilio/MSG91 by implementing OtpProvider.
 */
@Slf4j
@Service
public class MockMobileOtpProviderImpl implements OtpProvider {

    @Override
    public void sendOtp(String target, String otp) {
        log.info("\n======================================================\n" +
                "MOCK MOBILE SMS SENT TO: {}\n" +
                "OTP IS: {}\n" +
                "======================================================", target, otp);
    }

    @Override
    public OtpTargetType getTargetType() {
        return OtpTargetType.MOBILE;
    }
}