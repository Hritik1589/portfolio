package com.hritik.portfolio.service.impl;
import com.hritik.portfolio.dto.request.ContactMessageRequest;
import com.hritik.portfolio.dto.response.ContactMessageResponse;
import com.hritik.portfolio.entity.ContactMessage;
import com.hritik.portfolio.event.ContactMessageEvent;
import com.hritik.portfolio.event.EmailNotificationEvent;
import com.hritik.portfolio.exception.ResourceNotFoundException;
import com.hritik.portfolio.mapper.ContactMessageMapper;
import com.hritik.portfolio.producer.KafkaEventProducer;
import com.hritik.portfolio.repository.ContactMessageRepository;
import com.hritik.portfolio.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    private final KafkaEventProducer kafkaEventProducer;

    @Value("${app.admin.email:hello@hritiknegi.com}")
    private String adminEmail;

    @Override
    @Transactional
    public void submitMessage(ContactMessageRequest request) {
        ContactMessage message = contactMessageMapper.toEntity(request);
        ContactMessage savedMessage = contactMessageRepository.save(message);

        // 1. Publish Event for WebSocket/Audit tracking
        ContactMessageEvent event = ContactMessageEvent.builder()
                .messageId(savedMessage.getId())
                .senderName(savedMessage.getName())
                .senderEmail(savedMessage.getEmail())
                .subject(savedMessage.getSubject())
                .timestamp(LocalDateTime.now())
                .build();
        kafkaEventProducer.publishContactMessageEvent(event);

        // 2. Publish Event to trigger an email to the Admin
        EmailNotificationEvent emailEvent = EmailNotificationEvent.builder()
                .to(adminEmail)
                .subject("New Portfolio Contact: " + savedMessage.getSubject())
                .body("You have received a new message from " + savedMessage.getName() + " (" + savedMessage.getEmail() + ").\n\n" + savedMessage.getMessage())
                .build();
        kafkaEventProducer.publishEmailNotificationEvent(emailEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageResponse> getAllMessages(String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            return contactMessageRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSubjectContainingIgnoreCase(search, search, search, pageable)
                    .map(contactMessageMapper::toResponse);
        }
        return contactMessageRepository.findAll(pageable)
                .map(contactMessageMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageResponse> getUnreadMessages(Pageable pageable) {
        return contactMessageRepository.findByIsReadFalse(pageable)
                .map(contactMessageMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactMessageResponse getMessageById(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        return contactMessageMapper.toResponse(message);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        message.setRead(true);
        contactMessageRepository.save(message);
    }

    @Override
    @Transactional
    public void deleteMessage(Long id) {
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found");
        }
        contactMessageRepository.deleteById(id); // Handled by @SQLDelete (Soft Delete)
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return contactMessageRepository.countByIsReadFalse();
    }
}