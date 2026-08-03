package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.AchievementRequest;
import com.hritik.portfolio.dto.response.AchievementResponse;
import java.util.List;

public interface AchievementService {
    AchievementResponse createAchievement(AchievementRequest request);
    AchievementResponse updateAchievement(Long id, AchievementRequest request);
    void deleteAchievement(Long id);
    AchievementResponse getAchievementById(Long id);
    List<AchievementResponse> getAllAchievements();
}