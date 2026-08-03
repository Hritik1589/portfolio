package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.AboutResponse;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.service.AboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/about")
@RequiredArgsConstructor
@Tag(name = "Public About", description = "Public endpoint for portfolio about information")
public class PublicAboutController {

    private final AboutService aboutService;

    @Operation(summary = "Get About Me information")
    @GetMapping
    public ResponseEntity<ApiResponse<AboutResponse>> getAbout() {
        return ResponseEntity.ok(ApiResponse.success("About information fetched successfully", aboutService.getAboutInfo()));
    }
}
