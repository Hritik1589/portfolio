package com.hritik.portfolio.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hritik.portfolio.config.KafkaConfig;
import com.hritik.portfolio.event.ContactMessageEvent;
import com.hritik.portfolio.event.EmailNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConfig.TOPIC_CONTACT_MESSAGE, groupId = "portfolio-group")
    public void consumeContactMessage(String messagePayload) {
        try {
            // 1. Convert the incoming JSON string back to a Java Object
            ContactMessageEvent event = objectMapper.readValue(messagePayload, ContactMessageEvent.class);

            log.info("Consumed ContactMessageEvent -> Sender: {}, Subject: {}", event.getSenderName(), event.getSubject());
            // In Phase D, we will wire this to WebSocket alerts and auto-reply logic.

        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize ContactMessageEvent JSON", e);
        }
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_EMAIL_NOTIFICATION, groupId = "portfolio-group")
    public void consumeEmailNotification(String messagePayload) {
        try {
            EmailNotificationEvent event = objectMapper.readValue(messagePayload, EmailNotificationEvent.class);
            log.info("Consumed EmailNotificationEvent -> To: {}, Subject: {}", event.getTo(), event.getSubject());

            // In Phase B/D, we will wire this directly to JavaMailSender logic.
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize EmailNotificationEvent JSON", e);
        }
    }
}