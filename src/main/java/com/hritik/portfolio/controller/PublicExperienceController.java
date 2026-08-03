package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.ExperienceResponse;
import com.hritik.portfolio.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/experiences")
@RequiredArgsConstructor
@Tag(name = "Public Experiences", description = "Public endpoints for listing experience timeline")
public class PublicExperienceController {

    private final ExperienceService experienceService;

    @Operation(summary = "Get all experiences chronologically")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperiences() {
        return ResponseEntity.ok(ApiResponse.success("Experiences fetched successfully", experienceService.getAllExperiences()));
    }
}