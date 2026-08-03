package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.enums.SkillCategory;
import com.hritik.portfolio.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/skills")
@RequiredArgsConstructor
@Tag(name = "Public Skills", description = "Public endpoints for listing skills")
public class PublicSkillController {

    private final SkillService skillService;

    @Operation(summary = "Get all skills ordered by display order")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkills() {
        return ResponseEntity.ok(ApiResponse.success("Skills fetched successfully", skillService.getAllSkills()));
    }

    @Operation(summary = "Get skills by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getSkillsByCategory(@PathVariable SkillCategory category) {
        return ResponseEntity.ok(ApiResponse.success("Skills fetched successfully", skillService.getSkillsByCategory(category)));
    }
}
