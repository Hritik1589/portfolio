package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.SkillRequest;
import com.hritik.portfolio.dto.response.SkillResponse;
import com.hritik.portfolio.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toResponse(Skill skill);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Skill toEntity(SkillRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(SkillRequest request, @MappingTarget Skill skill);
}