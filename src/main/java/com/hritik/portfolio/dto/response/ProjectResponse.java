package com.hritik.portfolio.dto.response;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String slug;
    private String shortDescription;
    private String detailedDescription;
    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private boolean featured;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<SkillResponse> technologies;
}
