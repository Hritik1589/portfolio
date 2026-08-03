package com.hritik.portfolio.event;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageEvent {
    private Long messageId;
    private String senderName;
    private String senderEmail;
    private String subject;
    private LocalDateTime timestamp;
}
