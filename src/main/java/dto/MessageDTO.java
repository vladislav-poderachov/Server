package dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private String context;
    private LocalDateTime sendingTime;
    private LocalDateTime updatedAt;
    private Long senderId;
    private Long receiverId;
} 