package com.hritik.portfolio.dto.response;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private boolean isActive;
    private List<String> roles;
}
