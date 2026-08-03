package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutRepository extends JpaRepository<AboutMe, Long> {

    /**
     * The AboutMe section is typically a singleton record in the database.
     * This method fetches the first available record.
     */
    Optional<AboutMe> findFirstByOrderByIdAsc();
}