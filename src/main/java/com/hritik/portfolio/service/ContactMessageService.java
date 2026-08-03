package com.hritik.portfolio.service;
import com.hritik.portfolio.dto.request.ContactMessageRequest;
import com.hritik.portfolio.dto.response.ContactMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactMessageService {
    void submitMessage(ContactMessageRequest request);

    Page<ContactMessageResponse> getAllMessages(String search, Pageable pageable);
    Page<ContactMessageResponse> getUnreadMessages(Pageable pageable);

    ContactMessageResponse getMessageById(Long id);
    void markAsRead(Long id);
    void deleteMessage(Long id); // Soft delete / Archive
    long getUnreadCount();
}