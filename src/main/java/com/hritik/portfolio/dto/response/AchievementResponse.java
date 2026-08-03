package com.hritik.portfolio.dto.response;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AchievementResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String organization;
    private String certUrl;
    private String imageUrl;
}
