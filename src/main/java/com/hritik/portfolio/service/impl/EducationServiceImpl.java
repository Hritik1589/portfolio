package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.EducationRequest;
import com.hritik.portfolio.dto.response.EducationResponse;
import com.hritik.portfolio.entity.Education;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.EducationMapper;
import com.hritik.portfolio.repository.EducationRepository;
import com.hritik.portfolio.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    @Override
    @Transactional
    @CacheEvict(value = "education", allEntries = true)
    public EducationResponse createEducation(EducationRequest request) {
        Education education = educationMapper.toEntity(request);
        return educationMapper.toResponse(educationRepository.save(education));
    }

    @Override
    @Transactional
    @CacheEvict(value = "education", allEntries = true)
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with id: " + id));
        educationMapper.updateEntityFromRequest(request, education);
        return educationMapper.toResponse(educationRepository.save(education));
    }

    @Override
    @Transactional
    @CacheEvict(value = "education", allEntries = true)
    public void deleteEducation(Long id) {
        if (!educationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Education not found with id: " + id);
        }
        educationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EducationResponse getEducationById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with id: " + id));
        return educationMapper.toResponse(education);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "education", key = "'all'")
    public List<EducationResponse> getAllEducation() {
        return educationRepository.findAllByOrderByEndYearDesc().stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }
}