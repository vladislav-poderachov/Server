package dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long recipientId;
    private String content;
} 