package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.request.ContactMessageRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.service.ContactMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/contact")
@RequiredArgsConstructor
@Tag(name = "Public Contact", description = "Public endpoint for submitting contact messages")
public class PublicContactController {

    private final ContactMessageService contactMessageService;

    @Operation(summary = "Submit a contact message")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> submitMessage(@Valid @RequestBody ContactMessageRequest request) {
        contactMessageService.submitMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Your message has been sent successfully. I will get back to you soon!", null));
    }
}
