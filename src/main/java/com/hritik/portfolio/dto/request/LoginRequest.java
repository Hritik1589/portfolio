package com.hritik.portfolio.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email or mobile cannot be blank")
    private String username; // Can be email or mobile

    @NotBlank(message = "Password cannot be blank")
    private String password;
}