package com.hritik.portfolio.dto.request;
import com.hritik.portfolio.enums.OtpTargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VerifyOtpRequest {
    @NotBlank(message = "Target cannot be empty")
    private String target;

    @NotBlank(message = "OTP cannot be empty")
    private String otp;

    @NotNull(message = "Target type is required")
    private OtpTargetType targetType;
}