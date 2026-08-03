package com.hritik.portfolio.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String mobile;
}