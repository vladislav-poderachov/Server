package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationRequest {
    private String title;
    private String content;
    private Long categoryId;
    private Long[] photoIds;
} 