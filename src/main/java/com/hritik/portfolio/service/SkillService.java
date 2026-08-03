package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.SkillRequest;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.enums.SkillCategory;

import java.util.List;

public interface SkillService {
    SkillResponse createSkill(SkillRequest request);
    SkillResponse updateSkill(Long id, SkillRequest request);
    void deleteSkill(Long id);
    SkillResponse getSkillById(Long id);
    List<SkillResponse> getAllSkills();
    List<SkillResponse> getSkillsByCategory(SkillCategory category);
}
