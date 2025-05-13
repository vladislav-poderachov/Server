package dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class YouTubeVideoDTO {
    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelTitle;
    private String publishedAt;
    private Long viewCount;
    private Long likeCount;
    private String duration;
    private String url;
} 