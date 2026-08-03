package com.hritik.portfolio.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AboutRequest {
    @NotBlank(message = "Summary (Hero Content) is required")
    private String summary;

    @NotBlank(message = "Career Journey (Biography) is required")
    private String careerJourney;

    @NotBlank(message = "Current Focus is required")
    private String currentFocus;

    @NotBlank(message = "Goals are required")
    private String goals;
}