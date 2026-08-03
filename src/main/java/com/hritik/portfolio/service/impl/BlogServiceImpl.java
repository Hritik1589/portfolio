package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.BlogRequest;
import com.hritik.portfolio.dto.response.BlogResponse;
import com.hritik.portfolio.dto.response.PageResponse;
import com.hritik.portfolio.entity.Blog;
import com.hritik.portfolio.enums.BlogStatus;
import com.hritik.portfolio.exception.BadRequestException;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.BlogMapper;
import com.hritik.portfolio.repository.BlogRepository;
import com.hritik.portfolio.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    @Transactional
    @CacheEvict(value = "blogs", allEntries = true)
    public BlogResponse createBlog(BlogRequest request) {
        Blog blog = blogMapper.toEntity(request);
        blog.setSlug(generateSlug(request.getTitle()));

        if (request.getStatus() == null) {
            blog.setStatus(BlogStatus.DRAFT);
        }

        if (blog.getStatus() == BlogStatus.PUBLISHED) {
            blog.setPublishedDate(LocalDateTime.now());
        }

        return blogMapper.toResponse(blogRepository.save(blog));
    }

    @Override
    @Transactional
    @CacheEvict(value = "blogs", allEntries = true)
    public BlogResponse updateBlog(Long id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        // If title changed, update slug
        if (!blog.getTitle().equalsIgnoreCase(request.getTitle())) {
            blog.setSlug(generateSlug(request.getTitle()));
        }

        // 1. Save the existing status and published date to protect them
        BlogStatus existingStatus = blog.getStatus();
        LocalDateTime existingPublishedDate = blog.getPublishedDate();

        // 2. Do the mapping (this might accidentally turn status to null)
        blogMapper.updateEntityFromRequest(request, blog);

        // 3. Put the protected values back if the request didn't explicitly provide them
        if (blog.getStatus() == null) {
            blog.setStatus(existingStatus);
        }

        // Ensure published date isn't lost if the frontend didn't send it
        if (blog.getPublishedDate() == null && existingStatus == BlogStatus.PUBLISHED) {
            blog.setPublishedDate(existingPublishedDate);
        }

        return blogMapper.toResponse(blogRepository.save(blog));
    }

    @Override
    @Transactional
    @CacheEvict(value = "blogs", allEntries = true)
    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "blogs", allEntries = true)
    public BlogResponse publishBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        if (blog.getStatus() == BlogStatus.PUBLISHED) {
            throw new BadRequestException("Blog is already published");
        }

        blog.setStatus(BlogStatus.PUBLISHED);
        blog.setPublishedDate(LocalDateTime.now());
        return blogMapper.toResponse(blogRepository.save(blog));
    }

    @Override
    @Transactional
    @CacheEvict(value = "blogs", allEntries = true)
    public BlogResponse unpublishBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        blog.setStatus(BlogStatus.DRAFT);
        blog.setPublishedDate(null);
        return blogMapper.toResponse(blogRepository.save(blog));
    }

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));
        return blogMapper.toResponse(blog);
    }

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with slug: " + slug));
        return blogMapper.toResponse(blog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogResponse> getAllBlogsForAdmin(String search, Pageable pageable) {
        // Admin sees everything (Drafts, Published, Archived)
        return blogRepository.findAll(pageable).map(blogMapper::toResponse);
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "blogs", key = "{#search, #category, #pageable.pageNumber, #pageable.pageSize}")
    public PageResponse<BlogResponse> getPublishedBlogsForPublic(String search, String category, Pageable pageable) {

        // Calls the new all-in-one query from the Repository
        Page<Blog> blogPage = blogRepository.findPublishedBlogsWithFilters(
                BlogStatus.PUBLISHED,
                search,
                category,
                pageable
        );

        // Wraps the result in our safe DTO so Redis caches it perfectly
        return new PageResponse<>(blogPage.map(blogMapper::toResponse));
    }

    private String generateSlug(String title) {
        if (title == null) return "";
        String slug = title.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");

        // Ensure uniqueness (simple approach, appends random string if conflict)
        if (blogRepository.findBySlug(slug).isPresent()) {
            slug = slug + "-" + System.currentTimeMillis() % 1000;
        }
        return slug;
    }
}