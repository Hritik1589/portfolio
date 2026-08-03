package com.hritik.portfolio.controller;

import com.hritik.portfolio.dto.request.BlogRequest;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.BlogResponse;
import com.hritik.portfolio.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/blogs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Blogs", description = "Admin CMS endpoints for managing blogs")
public class AdminBlogController {

    private final BlogService blogService;

    @Operation(summary = "Create a new blog (Draft or Published)")
    @PostMapping
    public ResponseEntity<ApiResponse<BlogResponse>> createBlog(@Valid @RequestBody BlogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Blog created successfully", blogService.createBlog(request)));
    }

    @Operation(summary = "Update an existing blog")
    @PutMapping("/{id}")
    // 🚨 FIX: Added ("id")
    public ResponseEntity<ApiResponse<BlogResponse>> updateBlog(@PathVariable("id") Long id, @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Blog updated successfully", blogService.updateBlog(id, request)));
    }

    @Operation(summary = "Delete a blog (Soft Delete)")
    @DeleteMapping("/{id}")
    // 🚨 FIX: Added ("id")
    public ResponseEntity<ApiResponse<Void>> deleteBlog(@PathVariable("id") Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(ApiResponse.success("Blog deleted successfully", null));
    }

    @Operation(summary = "Publish a draft blog")
    @PatchMapping("/{id}/publish")
    // 🚨 FIX: Added ("id")
    public ResponseEntity<ApiResponse<BlogResponse>> publishBlog(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success("Blog published successfully", blogService.publishBlog(id)));
    }

    @Operation(summary = "Unpublish a blog (Revert to Draft)")
    @PatchMapping("/{id}/unpublish")
    // 🚨 FIX: Added ("id")
    public ResponseEntity<ApiResponse<BlogResponse>> unpublishBlog(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success("Blog unpublished successfully", blogService.unpublishBlog(id)));
    }

    @Operation(summary = "Get all blogs for Admin dashboard (Includes drafts)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BlogResponse>>> getAllBlogs(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success("Blogs fetched successfully", blogService.getAllBlogsForAdmin(search, pageable)));
    }

    @Operation(summary = "Get a blog by ID")
    @GetMapping("/{id}")
    // 🚨 FIX: Added ("id")
    public ResponseEntity<ApiResponse<BlogResponse>> getBlogById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success("Blog fetched successfully", blogService.getBlogById(id)));
    }
}