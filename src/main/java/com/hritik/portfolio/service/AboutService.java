package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.AboutRequest;
import com.hritik.portfolio.dto.response.AboutResponse;

public interface AboutService {
    AboutResponse getAboutInfo();
    AboutResponse updateAboutInfo(AboutRequest request);
}
