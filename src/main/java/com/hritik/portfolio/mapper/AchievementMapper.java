package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.AchievementRequest;
import com.hritik.portfolio.dto.response.AchievementResponse;
import com.hritik.portfolio.entity.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AchievementMapper {
    AchievementResponse toResponse(Achievement achievement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Achievement toEntity(AchievementRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(AchievementRequest request, @MappingTarget Achievement achievement);
}