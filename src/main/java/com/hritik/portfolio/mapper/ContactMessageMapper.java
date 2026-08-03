package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.ContactMessageRequest;
import com.hritik.portfolio.dto.response.ContactMessageResponse;
import com.hritik.portfolio.entity.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {

    ContactMessageResponse toResponse(ContactMessage contactMessage);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "read", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    ContactMessage toEntity(ContactMessageRequest request);
}