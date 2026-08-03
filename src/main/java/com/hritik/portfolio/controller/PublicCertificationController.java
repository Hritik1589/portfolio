package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.CertificationResponse;
import com.hritik.portfolio.service.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/certifications")
@RequiredArgsConstructor
@Tag(name = "Public Certifications", description = "Public endpoints for listing certifications")
public class PublicCertificationController {

    private final CertificationService certificationService;

    @Operation(summary = "Get all certifications")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificationResponse>>> getAllCertifications() {
        return ResponseEntity.ok(ApiResponse.success("Certifications fetched successfully", certificationService.getAllCertifications()));
    }
}
