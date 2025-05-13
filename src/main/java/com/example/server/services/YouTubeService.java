package services;

import java.util.List;
import dto.YouTubeVideoDTO;

public interface YouTubeService {
    List<YouTubeVideoDTO> searchVideos(String query, int maxResults);
    YouTubeVideoDTO getVideoDetails(String videoId);
    List<YouTubeVideoDTO> searchVideosByGoal(String goalTitle, String goalDescription, int maxResults);
    List<YouTubeVideoDTO> searchVideosByKeywords(List<String> keywords, int maxResults);
}