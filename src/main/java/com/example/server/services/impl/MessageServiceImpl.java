package com.example.server.services.impl;

import dto.MessageRequest;
import dto.MessageResponse;
import com.example.server.entities.Message;
import com.example.server.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.server.repositories.MessageRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.services.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MessageResponse sendMessage(Long senderId, MessageRequest request) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Отправитель не найден"));

        if (request.getRecipientId() == null) {
            throw new RuntimeException("Не указан получатель сообщения");
        }

        User recipient = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Получатель не найден"));

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(sender);
        message.setReceiver(recipient);
        message.setRead(true); // Отправитель уже прочитал сообщение

        Message savedMessage = messageRepository.save(message);
        return mapToResponse(savedMessage);
    }

    @Override
    public MessageResponse getMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Сообщение не найдено"));
        return mapToResponse(message);
    }

    @Override
    public List<MessageResponse> getUserMessages(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markMessageAsRead(Long userId, Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Сообщение не найдено"));
        
        if (!message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Пользователь не является получателем сообщения");
        }

        message.setRead(true);
        messageRepository.save(message);
    }

    @Override
    public Long getUnreadMessagesCount(Long userId) {
        return messageRepository.countByReceiverIdAndIsReadFalse(userId);
    }

    @Override
    public List<MessageResponse> getChatMessages(Long chatId) {
        return messageRepository.findBySenderIdOrReceiverId(chatId, chatId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAllChatMessagesAsRead(Long userId, Long chatId) {
        List<Message> unreadMessages = messageRepository.findByReceiverIdAndIsReadFalse(chatId);
        for (Message message : unreadMessages) {
            if (message.getReceiver().getId().equals(userId)) {
                message.setRead(true);
                messageRepository.save(message);
            }
        }
    }

    private MessageResponse mapToResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderLogin(message.getSender().getLogin())
                .senderId(message.getSender().getId())
                .recipientId(message.getReceiver().getId())
                .recipientLogin(message.getReceiver().getLogin())
                .isRead(message.isRead())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }
} 