package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.EducationRequest;
import com.hritik.portfolio.dto.response.EducationResponse;
import com.hritik.portfolio.entity.Education;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EducationMapper {
    EducationResponse toResponse(Education education);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Education toEntity(EducationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
   // @Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(EducationRequest request, @MappingTarget Education education);
}