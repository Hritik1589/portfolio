package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.ExperienceRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.EducationResponse;
import com.hritik.portfolio.dto.response.ExperienceResponse;
import com.hritik.portfolio.service.ExperienceService;
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
@RequestMapping("/api/v1/admin/experiences")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Experiences", description = "Admin endpoints for managing experiences")
public class AdminExperienceController {

    private final ExperienceService experienceService;

    @Operation(summary = "Create an experience entry")
    @PostMapping
    public ResponseEntity<ApiResponse<ExperienceResponse>> createExperience(@Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Experience created successfully", experienceService.createExperience(request)));
    }

    @Operation(summary = "Update an experience entry")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> updateExperience(@PathVariable Long id, @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Experience updated successfully", experienceService.updateExperience(id, request)));
    }

    @Operation(summary = "Delete an experience entry")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.ok(ApiResponse.success("Experience deleted successfully", null));
    }

    @Operation(summary = "Get an experience entry by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Experience fetched successfully", experienceService.getExperienceById(id)));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperience() {


        return ResponseEntity.ok(ApiResponse.success("Experience list fetched", experienceService.getAllExperiences()));
    }
}
