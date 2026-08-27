package com.hritik.portfolio.consumer;

import com.hritik.portfolio.event.ContactMessageEvent;
import com.hritik.portfolio.event.EmailNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaEventConsumer {

    // @Async ensure karega ki yeh Kafka ki tarah background thread mein chale
    @Async
    @EventListener
    public void consumeContactMessage(ContactMessageEvent event) {
        // Hamein JSON ko wapas object mein badalne (ObjectMapper) ki zaroorat nahi!
        log.info("Consumed ContactMessageEvent -> Sender: {}, Subject: {}", event.getSenderName(), event.getSubject());
        // In Phase D, we will wire this to WebSocket alerts and auto-reply logic.
    }

    @Async
    @EventListener
    public void consumeEmailNotification(EmailNotificationEvent event) {
        log.info("Consumed EmailNotificationEvent -> To: {}, Subject: {}", event.getTo(), event.getSubject());
        // In Phase B/D, we will wire this directly to JavaMailSender logic.
    }
}