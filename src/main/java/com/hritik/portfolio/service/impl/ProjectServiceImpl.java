package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.ProjectRequest;
import com.hritik.portfolio.dto.response.ProjectResponse;
import com.hritik.portfolio.entity.Project;
import com.hritik.portfolio.entity.Skill;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.ProjectMapper;
import com.hritik.portfolio.repository.ProjectRepository;
import com.hritik.portfolio.repository.SkillRepository;
import com.hritik.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.toEntity(request);
        assignTechnologiesToProject(request.getTechnologies(), project);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponse(savedProject);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        projectMapper.updateEntityFromRequest(request, project);
        assignTechnologiesToProject(request.getTechnologies(), project);

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toResponse(updatedProject);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with slug: " + slug));
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponse> getAllProjects(String search, Pageable pageable) {
        Page<Project> projects;
        if (search != null && !search.isBlank()) {
            projects = projectRepository.searchProjectsWithTechnologies(search, pageable);
        } else {
            projects = projectRepository.findAll(pageable);
        }
        return projects.map(projectMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getFeaturedProjects() {
        return projectRepository.findByIsFeaturedTrue().stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Helper to map a set of IDs to actual Skill entities safely without polluting the mapper.
     */
    /**
     * Helper to map a set of IDs to actual Skill entities safely without polluting the mapper.
     */
    private void assignTechnologiesToProject(List<String> techNames, Project project) {
        Set<Skill> skills = new HashSet<>();

        if (techNames != null && !techNames.isEmpty()) {
            for (String name : techNames) {

                Skill skill = skillRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> {
                            Skill newSkill = new Skill();
                            newSkill.setName(name);

                            // 1. Default Category
                            newSkill.setCategory(com.hritik.portfolio.enums.SkillCategory.BACKEND);

                            // 2. Default Display Order
                            newSkill.setDisplayOrder(0);

                            // 🚨 3. FIX: Default Proficiency (Assuming it's an Integer 0-100)
                            // If your proficiency is an Enum, change this to your Enum like you did with Category!
                            newSkill.setProficiency(50);

                            // 🚨 4. FIX: Default Years of Experience (Just in case the DB asks for it next!)


                            return skillRepository.save(newSkill);
                        });
                skills.add(skill);
            }
        }

        project.setTechnologies(skills);
    }
}