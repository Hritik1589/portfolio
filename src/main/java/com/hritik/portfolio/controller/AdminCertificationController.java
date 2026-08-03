package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.CertificationRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.CertificationResponse;
import com.hritik.portfolio.service.CertificationService;
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
@RequestMapping("/api/v1/admin/certifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Certifications", description = "Admin endpoints for managing certifications")
public class AdminCertificationController {

    private final CertificationService certificationService;

    @Operation(summary = "Create a certification entry")
    @PostMapping
    public ResponseEntity<ApiResponse<CertificationResponse>> createCertification(@Valid @RequestBody CertificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Certification created successfully", certificationService.createCertification(request)));
    }

    @Operation(summary = "Update a certification entry")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificationResponse>> updateCertification(@PathVariable Long id, @Valid @RequestBody CertificationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Certification updated successfully", certificationService.updateCertification(id, request)));
    }

    @Operation(summary = "Delete a certification entry")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.ok(ApiResponse.success("Certification deleted successfully", null));
    }

    @Operation(summary = "Get a certification entry by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificationResponse>> getCertificationById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Certification fetched successfully", certificationService.getCertificationById(id)));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificationResponse>>> getAllCertifications() {
        // Call your service to return all certifications
        return ResponseEntity.ok(ApiResponse.success("Certifications fetched",certificationService.getAllCertifications()));
    }
}
