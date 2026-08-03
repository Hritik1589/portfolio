package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.SkillRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.service.SkillService;
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
@RequestMapping("/api/v1/admin/skills")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Skills", description = "Admin endpoints for managing skills")
public class AdminSkillController {

    private final SkillService skillService;

    @Operation(summary = "Create a new skill")
    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(@Valid @RequestBody SkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Skill created successfully", skillService.createSkill(request)));
    }

    @Operation(summary = "Update an existing skill")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Skill updated successfully", skillService.updateSkill(id, request)));
    }

    @Operation(summary = "Delete a skill")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(ApiResponse.success("Skill deleted successfully", null));
    }

    @Operation(summary = "Get a skill by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Skill fetched successfully", skillService.getSkillById(id)));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkills() {


        return ResponseEntity.ok(ApiResponse.success("Skill fetched successfully", skillService.getAllSkills()));
    }
}
