package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.AchievementRequest;
import com.hritik.portfolio.dto.response.AchievementResponse;
import com.hritik.portfolio.entity.Achievement;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.AchievementMapper;
import com.hritik.portfolio.repository.AchievementRepository;
import com.hritik.portfolio.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    @Override
    @Transactional
    @CacheEvict(value = "achievements", allEntries = true)
    public AchievementResponse createAchievement(AchievementRequest request) {
        Achievement achievement = achievementMapper.toEntity(request);
        return achievementMapper.toResponse(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    @CacheEvict(value = "achievements", allEntries = true)
    public AchievementResponse updateAchievement(Long id, AchievementRequest request) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id: " + id));
        achievementMapper.updateEntityFromRequest(request, achievement);
        return achievementMapper.toResponse(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    @CacheEvict(value = "achievements", allEntries = true)
    public void deleteAchievement(Long id) {
        if (!achievementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Achievement not found with id: " + id);
        }
        achievementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementResponse getAchievementById(Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id: " + id));
        return achievementMapper.toResponse(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "achievements", key = "'all'")
    public List<AchievementResponse> getAllAchievements() {
        return achievementRepository.findAllByOrderByDateDesc().stream()
                .map(achievementMapper::toResponse)
                .collect(Collectors.toList());
    }
}
