package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.ExperienceRequest;
import com.hritik.portfolio.dto.response.ExperienceResponse;
import com.hritik.portfolio.entity.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    ExperienceResponse toResponse(Experience experience);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Experience toEntity(ExperienceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
   // @Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(ExperienceRequest request, @MappingTarget Experience experience);
}