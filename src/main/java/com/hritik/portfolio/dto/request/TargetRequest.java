package com.hritik.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TargetRequest {
    @NotBlank(message = "Target (email or mobile) cannot be blank")
    private String target;
}
