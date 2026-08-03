package com.hritik.portfolio.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class ProjectRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Slug is required")
    private String slug;

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    @NotBlank(message = "Detailed description is required")
    private String detailedDescription;

    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private boolean featured;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    private LocalDate endDate;

    // Must be this:
    private List<String> technologies;
}
