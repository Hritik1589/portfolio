package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.AboutRequest;
import com.hritik.portfolio.dto.response.AboutResponse;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.service.AboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/about")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin About", description = "Admin endpoint for managing about information")
public class AdminAboutController {

    private final AboutService aboutService;

    @Operation(summary = "Update About Me information")
    @PutMapping
    public ResponseEntity<ApiResponse<AboutResponse>> updateAbout(@Valid @RequestBody AboutRequest request) {
        return ResponseEntity.ok(ApiResponse.success("About information updated successfully", aboutService.updateAboutInfo(request)));
    }
}