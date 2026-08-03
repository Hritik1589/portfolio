package com.hritik.portfolio.dto.response;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
}