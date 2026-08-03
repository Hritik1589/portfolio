package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.ProjectRequest;
import com.hritik.portfolio.dto.response.ProjectResponse;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.entity.Project;
import com.hritik.portfolio.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse toResponse(Project project);

    SkillResponse toSkillResponse(Skill skill);

    // Ignore technologies ID mapping here, handled in service to prevent DB queries inside mapper
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    //@Mapping(target = "updatedAt", ignore = true)
    Project toEntity(ProjectRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    //@Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(ProjectRequest request, @MappingTarget Project project);
}
