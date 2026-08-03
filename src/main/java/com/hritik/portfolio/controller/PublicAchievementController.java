package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.AchievementResponse;
import com.hritik.portfolio.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/achievements")
@RequiredArgsConstructor
@Tag(name = "Public Achievements", description = "Public endpoints for listing achievements")
public class PublicAchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Get all achievements")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AchievementResponse>>> getAllAchievements() {
        return ResponseEntity.ok(ApiResponse.success("Achievements fetched successfully", achievementService.getAllAchievements()));
    }
}