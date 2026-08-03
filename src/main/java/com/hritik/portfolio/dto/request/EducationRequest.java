package com.hritik.portfolio.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationRequest {
    @NotBlank(message = "Degree is required")
    private String degree;

    @NotBlank(message = "University is required")
    private String university;

    @NotNull(message = "Start year is required")
    private Integer startYear;

    private Integer endYear;
    private String gpaOrPercentage;
    private String description;
}