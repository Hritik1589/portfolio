package com.hritik.portfolio.dto.response;
import com.hritik.portfolio.enums.SkillCategory;
import lombok.Data;

@Data
public class SkillResponse {
    private Long id;
    private String name;
    private SkillCategory category;
    private Integer proficiency;
    private String iconUrl;
    private Double yearsOfExperience;
    private Integer displayOrder;
}
