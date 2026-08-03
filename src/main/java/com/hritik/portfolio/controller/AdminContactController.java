package com.hritik.portfolio.controller;
import com.hritik.portfolio.dto.response.ApiResponse;
import com.hritik.portfolio.dto.response.ContactMessageResponse;
import com.hritik.portfolio.service.ContactMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/messages")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Contact Messages", description = "Admin endpoints for managing contact submissions")
public class AdminContactController {

    private final ContactMessageService contactMessageService;

    @Operation(summary = "Get all messages with search and pagination")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ContactMessageResponse>>> getAllMessages(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success("Messages fetched successfully", contactMessageService.getAllMessages(search, pageable)));
    }

    @Operation(summary = "Get only unread messages")
    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<Page<ContactMessageResponse>>> getUnreadMessages(
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success("Unread messages fetched successfully", contactMessageService.getUnreadMessages(pageable)));
    }

    @Operation(summary = "Get total unread message count (for Dashboard Badge)")
    @GetMapping("/unread/count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        return ResponseEntity.ok(ApiResponse.success("Count fetched successfully", contactMessageService.getUnreadCount()));
    }

    @Operation(summary = "Get a message by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactMessageResponse>> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Message fetched successfully", contactMessageService.getMessageById(id)));
    }

    @Operation(summary = "Mark a message as read")
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        contactMessageService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Message marked as read", null));
    }

    @Operation(summary = "Delete (Archive/Soft Delete) a message")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable Long id) {
        contactMessageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }
}