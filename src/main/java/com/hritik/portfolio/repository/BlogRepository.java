package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Blog;
import com.hritik.portfolio.enums.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findBySlug(String slug);

    // Fetch blogs by their status (e.g., to only show PUBLISHED blogs to public users)
    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);

    // Filter by category and status
    Page<Blog> findByCategoryAndStatus(String category, BlogStatus status, Pageable pageable);

    // Full-text search emulation on title or content for published blogs
    @Query("SELECT b FROM Blog b WHERE b.status = :status AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Blog> searchPublishedBlogs(@Param("keyword") String keyword, @Param("status") BlogStatus status, Pageable pageable);
    @Query("SELECT b FROM Blog b WHERE b.status = :status " +
            "AND (:category IS NULL OR b.category = :category) " +
            "AND (:search IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.content) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Blog> findPublishedBlogsWithFilters(
            @Param("status") BlogStatus status,
            @Param("search") String search,
            @Param("category") String category,
            Pageable pageable);
}
