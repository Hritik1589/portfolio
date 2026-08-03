package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.AchievementRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.AchievementResponse;
import com.hritik.portfolio.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/achievements")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Achievements", description = "Admin endpoints for managing achievements")
public class AdminAchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Create an achievement entry")
    @PostMapping
    public ResponseEntity<ApiResponse<AchievementResponse>> createAchievement(@Valid @RequestBody AchievementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Achievement created successfully", achievementService.createAchievement(request)));
    }

    @Operation(summary = "Update an achievement entry")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AchievementResponse>> updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Achievement updated successfully", achievementService.updateAchievement(id, request)));
    }

    @Operation(summary = "Delete an achievement entry")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.ok(ApiResponse.success("Achievement deleted successfully", null));
    }

    @Operation(summary = "Get an achievement entry by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AchievementResponse>> getAchievementById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Achievement fetched successfully", achievementService.getAchievementById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AchievementResponse>>> getAllAchievements() {
        // Call your service to return all achievements

        return ResponseEntity.ok(ApiResponse.success("Achievements fetched",achievementService.getAllAchievements()));
    }
}
