package com.hritik.portfolio.mapper;
import com.hritik.portfolio.dto.request.BlogRequest;
import com.hritik.portfolio.dto.response.BlogResponse;
import com.hritik.portfolio.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    @Mapping(target = "readingTimeMinutes", source = "content", qualifiedByName = "calculateReadingTime")
    BlogResponse toResponse(Blog blog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "publishedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    Blog toEntity(BlogRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "publishedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(BlogRequest request, @MappingTarget Blog blog);

    @Named("calculateReadingTime")
    default Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) return 1;
        // Average reading speed is 200 words per minute
        int wordCount = content.split("\\s+").length;
        return Math.max(1, (int) Math.ceil((double) wordCount / 200));
    }
}
