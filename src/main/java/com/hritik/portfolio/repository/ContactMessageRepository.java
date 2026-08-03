package com.hritik.portfolio.repository;
import com.hritik.portfolio.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    // Fetch only unread messages for the admin dashboard notifications
    Page<ContactMessage> findByIsReadFalse(Pageable pageable);

    // Search messages by sender name, email, or subject
    Page<ContactMessage> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSubjectContainingIgnoreCase(String name, String email, String subject, Pageable pageable);

    // Count unread messages
    long countByIsReadFalse();
}
