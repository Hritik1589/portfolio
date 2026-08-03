package com.hritik.portfolio.config;

import com.hritik.portfolio.interceptor.VisitorTrackingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final VisitorTrackingInterceptor visitorTrackingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Only track visitors hitting the public endpoints
        registry.addInterceptor(visitorTrackingInterceptor)
                .addPathPatterns("/api/v1/public/**");
    }
}