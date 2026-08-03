package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.SkillRequest;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.entity.Skill;
import com.hritik.portfolio.enums.SkillCategory;
import com.hritik.portfolio.exception.BadRequestException;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.SkillMapper;
import com.hritik.portfolio.repository.SkillRepository;
import com.hritik.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public SkillResponse createSkill(SkillRequest request) {
        if (skillRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BadRequestException("Skill with this name already exists");
        }
        Skill skill = skillMapper.toEntity(request);
        return skillMapper.toResponse(skillRepository.save(skill));
    }

    @Override
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        skillMapper.updateEntityFromRequest(request, skill);
        return skillMapper.toResponse(skillRepository.save(skill));
    }

    @Override
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        return skillMapper.toResponse(skill);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "skills", key = "'all'")
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(skillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "skills", key = "#category.name()")
    public List<SkillResponse> getSkillsByCategory(SkillCategory category) {
        return skillRepository.findByCategoryOrderByDisplayOrderAsc(category).stream()
                .map(skillMapper::toResponse)
                .collect(Collectors.toList());
    }
}