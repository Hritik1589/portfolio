package com.hritik.portfolio.dto.response;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactMessageResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String subject;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}
