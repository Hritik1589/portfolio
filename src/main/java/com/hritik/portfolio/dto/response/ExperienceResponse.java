package com.hritik.portfolio.dto.response;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExperienceResponse {
    private Long id;
    private String company;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
    private String description;
    private String achievements;
}