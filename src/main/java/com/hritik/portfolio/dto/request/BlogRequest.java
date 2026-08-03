package com.hritik.portfolio.dto.request;
import com.hritik.portfolio.enums.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private String coverImage;
    private String category;

    private BlogStatus status; // Optional: Can explicitly set to DRAFT or PUBLISHED on creation
}
