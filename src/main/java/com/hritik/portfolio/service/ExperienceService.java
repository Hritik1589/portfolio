package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.ExperienceRequest;
import com.hritik.portfolio.dto.response.ExperienceResponse;
import java.util.List;

public interface ExperienceService {
    ExperienceResponse createExperience(ExperienceRequest request);
    ExperienceResponse updateExperience(Long id, ExperienceRequest request);
    void deleteExperience(Long id);
    ExperienceResponse getExperienceById(Long id);
    List<ExperienceResponse> getAllExperiences();
}