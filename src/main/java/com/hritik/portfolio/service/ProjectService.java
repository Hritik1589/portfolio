package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.ProjectRequest;
import com.hritik.portfolio.dto.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    ProjectResponse updateProject(Long id, ProjectRequest request);
    void deleteProject(Long id);
    ProjectResponse getProjectById(Long id);
    ProjectResponse getProjectBySlug(String slug);
    Page<ProjectResponse> getAllProjects(String search, Pageable pageable);
    List<ProjectResponse> getFeaturedProjects();
}