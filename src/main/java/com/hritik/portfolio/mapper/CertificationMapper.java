package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.CertificationRequest;
import com.hritik.portfolio.dto.response.CertificationResponse;
import com.hritik.portfolio.entity.Certification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CertificationMapper {
    CertificationResponse toResponse(Certification certification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Certification toEntity(CertificationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
   // @Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(CertificationRequest request, @MappingTarget Certification certification);
}