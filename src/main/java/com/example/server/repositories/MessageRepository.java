package com.example.server.repositories;

import com.example.server.entities.Message;
import com.example.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAtDesc(User user1, User user2, User user3, User user4);
    List<Message> findBySenderOrReceiverOrderByCreatedAtDesc(User user1, User user2);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
    Long countByReceiverIdAndIsReadFalse(Long receiverId);
    List<Message> findByReceiverIdAndIsReadFalse(Long receiverId);
} 