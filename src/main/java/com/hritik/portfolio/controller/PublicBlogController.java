package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.BlogResponse;
import com.hritik.portfolio.dto.response.PageResponse;
import com.hritik.portfolio.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/blogs")
@RequiredArgsConstructor
@Tag(name = "Public Blogs", description = "Public endpoints for reading published blogs")
public class PublicBlogController {

    private final BlogService blogService;

    @Operation(summary = "Get all published blogs with pagination, search, and category filtering")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BlogResponse>>> getPublishedBlogs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10, sort = "publishedDate", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {

        // 🚨 FIX 2: Changed Page to PageResponse here as well
        PageResponse<BlogResponse> blogs = blogService.getPublishedBlogsForPublic(search, category, pageable);
        return ResponseEntity.ok(ApiResponse.success("Blogs fetched successfully", blogs));
    }

    @Operation(summary = "Get a published blog by its slug")
    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BlogResponse>> getBlogBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success("Blog fetched successfully", blogService.getBlogBySlug(slug)));
    }
}