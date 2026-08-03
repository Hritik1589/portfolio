package com.hritik.portfolio.service;

import com.hritik.portfolio.dto.request.CertificationRequest;
import com.hritik.portfolio.dto.response.CertificationResponse;
import java.util.List;

public interface CertificationService {
    CertificationResponse createCertification(CertificationRequest request);
    CertificationResponse updateCertification(Long id, CertificationRequest request);
    void deleteCertification(Long id);
    CertificationResponse getCertificationById(Long id);
    List<CertificationResponse> getAllCertifications();
}
