package com.hritik.portfolio.event;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationEvent {
    private String to;
    private String subject;
    private String body;
}
