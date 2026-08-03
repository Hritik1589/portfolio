package com.hritik.portfolio.service;

public interface VisitorService {
    void recordVisit(String ipAddress, String userAgent);
    long getTotalVisitorsCount();
}