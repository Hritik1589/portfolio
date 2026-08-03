package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    // Sort chronological experience, most recent first based on start date
    List<Experience> findAllByOrderByStartDateDesc();

    // Fetch only current active roles
    List<Experience> findByIsCurrentTrueOrderByStartDateDesc();
}
