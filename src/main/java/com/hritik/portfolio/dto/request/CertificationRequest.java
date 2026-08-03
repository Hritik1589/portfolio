package com.hritik.portfolio.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificationRequest {
    @NotBlank(message = "Certificate name is required")
    private String certificateName;

    @NotBlank(message = "Issuing organization is required")
    private String issuingOrganization;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    private String credentialId;
    private String credentialUrl;
}