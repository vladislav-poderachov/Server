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
public class CommentResponse {
    private Long id;
    private String content;
    private String authorLogin;
    private Long publicationId;
    private Long parentCommentId;
    private List<CommentResponse> replies;
    private int likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 