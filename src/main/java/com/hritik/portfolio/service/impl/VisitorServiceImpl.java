package com.hritik.portfolio.service.impl;

import com.hritik.portfolio.entity.SiteVisitor;
import com.hritik.portfolio.repository.SiteVisitorRepository;
import com.hritik.portfolio.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    private final SiteVisitorRepository visitorRepository;

    @Async // Runs in a separate thread so it doesn't block the API response
    @Override
    @Transactional
    public void recordVisit(String ipAddress, String userAgent) {
        try {
            SiteVisitor visitor = visitorRepository.findByIpAddress(ipAddress)
                    .orElseGet(() -> SiteVisitor.builder()
                            .ipAddress(ipAddress)
                            .visitCount(0)
                            .build());

            visitor.setVisitCount(visitor.getVisitCount() + 1);
            visitor.setLastVisitDate(LocalDateTime.now());

            // Truncate User-Agent to avoid DB column overflow if it's too long
            if (userAgent != null && userAgent.length() > 250) {
                userAgent = userAgent.substring(0, 250);
            }
            visitor.setLastUserAgent(userAgent);

            visitorRepository.save(visitor);
        } catch (Exception e) {
            log.error("Failed to record site visitor asynchronously", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalVisitorsCount() {
        return visitorRepository.count();
    }
}