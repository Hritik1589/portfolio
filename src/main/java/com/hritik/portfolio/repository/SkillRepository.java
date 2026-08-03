package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Skill;
import com.hritik.portfolio.enums.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // Group and order skills for the frontend UI visualizer
    List<Skill> findAllByOrderByDisplayOrderAsc();

    // Fetch skills by a specific category (e.g., FRONTEND, BACKEND)
    List<Skill> findByCategoryOrderByDisplayOrderAsc(SkillCategory category);

    boolean existsByNameIgnoreCase(String name);

    Optional<Skill> findByNameIgnoreCase(String name);
}