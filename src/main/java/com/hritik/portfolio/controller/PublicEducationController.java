package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.EducationResponse;
import com.hritik.portfolio.service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/education")
@RequiredArgsConstructor
@Tag(name = "Public Education", description = "Public endpoints for listing education timeline")
public class PublicEducationController {

    private final EducationService educationService;

    @Operation(summary = "Get all education records chronologically")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducation() {
        return ResponseEntity.ok(ApiResponse.success("Education fetched successfully", educationService.getAllEducation()));
    }
}