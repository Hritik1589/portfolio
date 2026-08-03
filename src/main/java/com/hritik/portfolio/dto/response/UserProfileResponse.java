package com.hritik.portfolio.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Set<String> roles;
    @JsonProperty("isEmailVerified")
    private boolean isEmailVerified;
}
