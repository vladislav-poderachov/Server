package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationResponse {
    private Long id;
    private String title;
    private String content;
    private String authorLogin;
    private String categoryName;
    private List<String> photoUrls;
    private int likesCount;
    private int commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 