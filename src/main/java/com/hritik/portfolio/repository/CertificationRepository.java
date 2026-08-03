package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.Certification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    // Sort certifications by issue date, newest first
    List<Certification> findAllByOrderByIssueDateDesc();

    // Paginated search by certificate name or issuing organization
    Page<Certification> findByCertificateNameContainingIgnoreCaseOrIssuingOrganizationContainingIgnoreCase(String name, String org, Pageable pageable);
}
