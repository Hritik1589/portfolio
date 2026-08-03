package com.hritik.portfolio.strategy.otp;
import com.hritik.portfolio.enums.OtpTargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailOtpProviderImpl implements OtpProvider {

    private final JavaMailSender mailSender;

    @Async // Runs on a background thread so the API responds instantly
    @Override
    public void sendOtp(String target, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(target);
            message.setSubject("Your Verification OTP - Hritik Negi Portfolio");
            message.setText("Your OTP for account verification is: " + otp + "\n\nThis OTP is valid for 10 minutes. Do not share it with anyone.");

            mailSender.send(message);
            log.info("Email OTP sent successfully to {}", target);
        } catch (Exception e) {
            log.error("Failed to send Email OTP to {}: {}", target, e.getMessage());
            throw new RuntimeException("Failed to send OTP email. Please try again later.");
        }
    }

    @Override
    public OtpTargetType getTargetType() {
        return OtpTargetType.EMAIL;
    }
}
