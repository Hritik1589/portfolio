package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    // Sort education history by the most recent end year (nulls first if currently studying)
    List<Education> findAllByOrderByEndYearDesc();
}
