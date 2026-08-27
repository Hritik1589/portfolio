package com.hritik.portfolio.producer;

import com.hritik.portfolio.event.ContactMessageEvent;
import com.hritik.portfolio.event.EmailNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {

    // KafkaTemplate ki jagah Spring ka apna Event Publisher use karenge
    private final ApplicationEventPublisher eventPublisher;

    // 1. Handles the Contact Message Event
    public void publishContactMessageEvent(ContactMessageEvent event) {
        log.info("Publishing contact message event for Message ID: {}", event.getMessageId());
        // Direct event bhej rahe hain (No JSON conversion needed!)
        eventPublisher.publishEvent(event);
    }

    // 2. Handles the Email Notification Event
    public void publishEmailNotificationEvent(EmailNotificationEvent event) {
        log.info("Publishing email notification event to: {}", event.getTo());
        eventPublisher.publishEvent(event);
    }
}