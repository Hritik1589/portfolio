package com.hritik.portfolio.mapper;

import com.hritik.portfolio.dto.request.AboutRequest;
import com.hritik.portfolio.dto.response.AboutResponse;
import com.hritik.portfolio.entity.AboutMe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AboutMapper {
    AboutResponse toResponse(AboutMe aboutMe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    AboutMe toEntity(AboutRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(AboutRequest request, @MappingTarget AboutMe aboutMe);
}