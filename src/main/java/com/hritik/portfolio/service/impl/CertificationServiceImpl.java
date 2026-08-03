package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.CertificationRequest;
import com.hritik.portfolio.dto.response.CertificationResponse;
import com.hritik.portfolio.entity.Certification;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.CertificationMapper;
import com.hritik.portfolio.repository.CertificationRepository;
import com.hritik.portfolio.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    @Override
    @Transactional
    @CacheEvict(value = "certifications", allEntries = true)
    public CertificationResponse createCertification(CertificationRequest request) {
        Certification certification = certificationMapper.toEntity(request);
        return certificationMapper.toResponse(certificationRepository.save(certification));
    }

    @Override
    @Transactional
    @CacheEvict(value = "certifications", allEntries = true)
    public CertificationResponse updateCertification(Long id, CertificationRequest request) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found with id: " + id));
        certificationMapper.updateEntityFromRequest(request, certification);
        return certificationMapper.toResponse(certificationRepository.save(certification));
    }

    @Override
    @Transactional
    @CacheEvict(value = "certifications", allEntries = true)
    public void deleteCertification(Long id) {
        if (!certificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certification not found with id: " + id);
        }
        certificationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CertificationResponse getCertificationById(Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found with id: " + id));
        return certificationMapper.toResponse(certification);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "certifications", key = "'all'")
    public List<CertificationResponse> getAllCertifications() {
        return certificationRepository.findAllByOrderByIssueDateDesc().stream()
                .map(certificationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
