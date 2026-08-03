package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.EducationRequest;
import com.hritik.portfolio.dto.response.EducationResponse;
import java.util.List;

public interface EducationService {
    EducationResponse createEducation(EducationRequest request);
    EducationResponse updateEducation(Long id, EducationRequest request);
    void deleteEducation(Long id);
    EducationResponse getEducationById(Long id);
    List<EducationResponse> getAllEducation();
}
