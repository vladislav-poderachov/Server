package dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long authorId;
    private Long publicationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 