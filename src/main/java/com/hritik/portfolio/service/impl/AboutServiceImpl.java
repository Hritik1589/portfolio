package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.AboutRequest;
import com.hritik.portfolio.dto.response.AboutResponse;
import com.hritik.portfolio.entity.AboutMe;
import com.hritik.portfolio.mapper.AboutMapper;
import com.hritik.portfolio.repository.AboutRepository;
import com.hritik.portfolio.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;
    private final AboutMapper aboutMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "about")
    public AboutResponse getAboutInfo() {
        AboutMe aboutMe = aboutRepository.findFirstByOrderByIdAsc()
                .orElse(new AboutMe()); // Return empty object if not found
        return aboutMapper.toResponse(aboutMe);
    }

    @Override
    @Transactional
    @CacheEvict(value = "about", allEntries = true)
    public AboutResponse updateAboutInfo(AboutRequest request) {
        AboutMe aboutMe = aboutRepository.findFirstByOrderByIdAsc()
                .orElse(new AboutMe());

        aboutMapper.updateEntityFromRequest(request, aboutMe);
        AboutMe saved = aboutRepository.save(aboutMe);
        return aboutMapper.toResponse(saved);
    }
}