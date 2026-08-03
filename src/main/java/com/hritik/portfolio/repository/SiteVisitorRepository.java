package com.hritik.portfolio.repository;

import com.hritik.portfolio.entity.SiteVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteVisitorRepository extends JpaRepository<SiteVisitor, Long> {
    Optional<SiteVisitor> findByIpAddress(String ipAddress);
}