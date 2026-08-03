package com.hritik.portfolio.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_CONTACT_MESSAGE = "contact-messages";
    public static final String TOPIC_EMAIL_NOTIFICATION = "email-notifications";
    public static final String TOPIC_AUDIT_LOG = "audit-logs";

    @Bean
    public NewTopic contactMessageTopic() {
        return TopicBuilder.name(TOPIC_CONTACT_MESSAGE)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emailNotificationTopic() {
        return TopicBuilder.name(TOPIC_EMAIL_NOTIFICATION)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic auditLogTopic() {
        return TopicBuilder.name(TOPIC_AUDIT_LOG)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
