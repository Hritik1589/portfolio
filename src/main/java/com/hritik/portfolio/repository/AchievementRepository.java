package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    // Fetch all achievements sorted by the most recent date
    List<Achievement> findAllByOrderByDateDesc();

    // Paginated search for achievements by title or organization
    Page<Achievement> findByTitleContainingIgnoreCaseOrOrganizationContainingIgnoreCase(String title, String organization, Pageable pageable);
}