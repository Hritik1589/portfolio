package com.hritik.portfolio.controller;

import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/visitors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Dashboard", description = "Admin endpoints for dashboard metrics")
public class AdminDashboardController {

    private final VisitorService visitorService;

    @Operation(summary = "Get total unique visitors count")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getVisitorCount() {
        return ResponseEntity.ok(ApiResponse.success("Visitor count fetched successfully", visitorService.getTotalVisitorsCount()));
    }
}