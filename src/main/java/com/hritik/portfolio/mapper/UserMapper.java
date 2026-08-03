package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.response.UserResponse;
import com.hritik.portfolio.entity.Role;
import com.hritik.portfolio.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserResponse toResponse(User user);

    @Named("mapRoles")
    default List<String> mapRoles(Set<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }
}
