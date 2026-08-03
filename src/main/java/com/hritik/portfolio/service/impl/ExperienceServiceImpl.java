package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.ExperienceRequest;
import com.hritik.portfolio.dto.response.ExperienceResponse;
import com.hritik.portfolio.entity.Experience;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.ExperienceMapper;
import com.hritik.portfolio.repository.ExperienceRepository;
import com.hritik.portfolio.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional
    @CacheEvict(value = "experiences", allEntries = true)
    public ExperienceResponse createExperience(ExperienceRequest request) {
        Experience experience = experienceMapper.toEntity(request);
        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    @CacheEvict(value = "experiences", allEntries = true)
    public ExperienceResponse updateExperience(Long id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
        experienceMapper.updateEntityFromRequest(request, experience);
        return experienceMapper.toResponse(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    @CacheEvict(value = "experiences", allEntries = true)
    public void deleteExperience(Long id) {
        if (!experienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found with id: " + id);
        }
        experienceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
        return experienceMapper.toResponse(experience);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "experiences", key = "'all'")
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAllByOrderByStartDateDesc().stream()
                .map(experienceMapper::toResponse)
                .collect(Collectors.toList());
    }
}