package dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicationDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> photoIds;
    private List<Long> commentIds;
    private List<Long> goalIds;
} 