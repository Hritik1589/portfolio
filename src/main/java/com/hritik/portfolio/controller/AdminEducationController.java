package com.hritik.portfolio.controller;

import com.hritik.portfolio.dto.request.EducationRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.EducationResponse;
import com.hritik.portfolio.service.EducationService;
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
@RequestMapping("/api/v1/admin/education")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Education", description = "Admin endpoints for managing education")
public class AdminEducationController {

    private final EducationService educationService;

    @Operation(summary = "Create an education entry")
    @PostMapping
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Education created successfully", educationService.createEducation(request)));
    }

    @Operation(summary = "Update an education entry")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(@PathVariable Long id, @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Education updated successfully", educationService.updateEducation(id, request)));
    }

    @Operation(summary = "Delete an education entry")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted successfully", null));
    }

    @Operation(summary = "Get an education entry by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getEducationById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Education fetched successfully", educationService.getEducationById(id)));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducation() {
        // Call your service to return all education entries

        return ResponseEntity.ok(ApiResponse.success("Education list fetched", educationService.getAllEducation()));
    }
}