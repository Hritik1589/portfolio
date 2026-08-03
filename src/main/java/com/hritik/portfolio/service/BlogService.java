package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.BlogRequest;
import com.hritik.portfolio.dto.response.BlogResponse;
import com.hritik.portfolio.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    BlogResponse createBlog(BlogRequest request);
    BlogResponse updateBlog(Long id, BlogRequest request);
    void deleteBlog(Long id);

    BlogResponse publishBlog(Long id);
    BlogResponse unpublishBlog(Long id);

    BlogResponse getBlogById(Long id);
    BlogResponse getBlogBySlug(String slug);

    Page<BlogResponse> getAllBlogsForAdmin(String search, Pageable pageable);
    // Change this line in BlogService.java:
    PageResponse<BlogResponse> getPublishedBlogsForPublic(String search, String category, Pageable pageable);

}
