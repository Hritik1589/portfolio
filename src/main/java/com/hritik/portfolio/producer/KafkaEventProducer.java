package com.hritik.portfolio.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hritik.portfolio.config.KafkaConfig;
import com.hritik.portfolio.event.ContactMessageEvent;
import com.hritik.portfolio.event.EmailNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {

    // Kafka is configured to expect Strings
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // 1. Handles the Contact Message Event
    public void publishContactMessageEvent(ContactMessageEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(KafkaConfig.TOPIC_CONTACT_MESSAGE, jsonMessage);

            // Using getMessageId() based on your service logic!
            log.info("Publishing contact message event for Message ID: {}", event.getMessageId());
        } catch (JsonProcessingException e) {
            log.error("Failed to convert ContactMessageEvent to JSON string", e);
        }
    }

    // 2. Handles the Email Notification Event (Missing previously!)
    public void publishEmailNotificationEvent(EmailNotificationEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(KafkaConfig.TOPIC_EMAIL_NOTIFICATION, jsonMessage);

            log.info("Publishing email notification event to: {}", event.getTo());
        } catch (JsonProcessingException e) {
            log.error("Failed to convert EmailNotificationEvent to JSON string", e);
        }
    }
}