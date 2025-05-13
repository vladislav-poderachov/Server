package com.example.server.services;

import dto.MessageRequest;
import dto.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(Long senderId, MessageRequest request);
    MessageResponse getMessage(Long messageId);
    List<MessageResponse> getUserMessages(Long userId);
    List<MessageResponse> getChatMessages(Long chatId);
    void markMessageAsRead(Long userId, Long messageId);
    void markAllChatMessagesAsRead(Long userId, Long chatId);
    Long getUnreadMessagesCount(Long userId);
} 