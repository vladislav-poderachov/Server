package com.example.server.controllers;

import com.example.server.config.SecurityUser;
import dto.MessageRequest;
import dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.server.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.sendMessage(securityUser.getUser().getId(), request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageResponse>> getChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageService.getChatMessages(chatId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponse>> getUserMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getUserMessages(userId));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markMessageAsRead(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long id) {
        messageService.markMessageAsRead(securityUser.getUser().getId(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chat/{chatId}/read-all")
    public ResponseEntity<Void> markAllChatMessagesAsRead(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long chatId) {
        messageService.markAllChatMessagesAsRead(securityUser.getUser().getId(), chatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread/count")
    public ResponseEntity<Long> getUnreadMessagesCount(
            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(messageService.getUnreadMessagesCount(securityUser.getUser().getId()));
    }
} 