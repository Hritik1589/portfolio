package com.hritik.portfolio.dto.response;
import lombok.Data;

@Data
public class EducationResponse {
    private Long id;
    private String degree;
    private String university;
    private Integer startYear;
    private Integer endYear;
    private String gpaOrPercentage;
    private String description;
}
