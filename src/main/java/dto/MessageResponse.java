package dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {
    private Long id;
    private String content;
    private String senderLogin;
    private Long senderId;
    private Long recipientId;
    private String recipientLogin;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 