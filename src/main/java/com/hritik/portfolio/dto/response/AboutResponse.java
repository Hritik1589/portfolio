package com.hritik.portfolio.dto.response;
import lombok.Data;

@Data
public class AboutResponse {
    private Long id;
    private String summary;
    private String careerJourney;
    private String currentFocus;
    private String goals;
}
