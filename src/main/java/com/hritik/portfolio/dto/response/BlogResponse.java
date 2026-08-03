package com.hritik.portfolio.dto.response;
import com.hritik.portfolio.enums.BlogStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BlogResponse {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String coverImage;
    private String category;
    private LocalDateTime publishedDate;
    private BlogStatus status;
    private Integer readingTimeMinutes;
}