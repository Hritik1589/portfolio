package com.hritik.portfolio.dto.response;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CertificationResponse {
    private Long id;
    private String certificateName;
    private String issuingOrganization;
    private LocalDate issueDate;
    private String credentialId;
    private String credentialUrl;
}