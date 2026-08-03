package com.hritik.portfolio.dto.request;
import com.hritik.portfolio.enums.SkillCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillRequest {
    @NotBlank(message = "Skill name is required")
    private String name;

    @NotNull(message = "Skill category is required")
    private SkillCategory category;

    @NotNull(message = "Proficiency is required")
    @Min(value = 0, message = "Proficiency cannot be less than 0")
    @Max(value = 100, message = "Proficiency cannot be more than 100")
    private Integer proficiency;

    private Double yearsOfExperience;

    private String iconUrl;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}
