package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.ProjectResponse;
import com.hritik.portfolio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/projects")
@RequiredArgsConstructor
@Tag(name = "Public Projects", description = "Public-facing read-only project endpoints")
public class PublicProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Get all projects with pagination and search")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getAllProjects(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<ProjectResponse> projects = projectService.getAllProjects(search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Projects fetched successfully", projects));
    }

    @Operation(summary = "Get featured projects for the portfolio homepage")
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getFeaturedProjects() {
        List<ProjectResponse> projects = projectService.getFeaturedProjects();
        return ResponseEntity.ok(ApiResponse.success("Featured projects fetched successfully", projects));
    }

    @Operation(summary = "Get project details by URL slug")
    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectBySlug(@PathVariable String slug) {
        ProjectResponse project = projectService.getProjectBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Project fetched successfully", project));
    }
}
