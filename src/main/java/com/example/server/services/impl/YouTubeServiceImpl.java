package com.example.server.services.impl;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import dto.YouTubeVideoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.server.services.YouTubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YouTubeServiceImpl implements YouTubeService {
    private static final Logger logger = LoggerFactory.getLogger(YouTubeServiceImpl.class);
    private static final String APPLICATION_NAME = "Development Goals App";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${youtube.api.key}")
    private String apiKey;

    private YouTube getYouTubeService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public List<YouTubeVideoDTO> searchVideos(String query, int maxResults) {
        try {
            YouTube.Search.List request = getYouTubeService().search()
                    .list(List.of("snippet"))
                    .setKey(apiKey)
                    .setQ(query)
                    .setMaxResults((long) maxResults)
                    .setType(List.of("video"));

            SearchListResponse response = request.execute();
            List<YouTubeVideoDTO> videos = new ArrayList<>();

            for (SearchResult result : response.getItems()) {
                videos.add(convertToDTO(result));
            }

            return videos;
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException("Error searching YouTube videos: " + e.getDetails().getMessage());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error searching YouTube videos", e);
        }
    }

    @Override
    public YouTubeVideoDTO getVideoDetails(String videoId) {
        try {
            YouTube.Videos.List request = getYouTubeService().videos()
                    .list(List.of("snippet", "statistics"))
                    .setKey(apiKey)
                    .setId(List.of(videoId));

            VideoListResponse response = request.execute();
            if (response.getItems().isEmpty()) {
                throw new RuntimeException("Video not found");
            }

            return convertToDTO(response.getItems().get(0));
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException("Error getting video details: " + e.getDetails().getMessage());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error getting video details", e);
        }
    }

    @Override
    public List<YouTubeVideoDTO> searchVideosByGoal(String goalTitle, String goalDescription, int maxResults) {
        if (goalTitle == null || goalDescription == null) {
            logger.warn("Goal title or description is null");
            return new ArrayList<>();
        }

        try {
            List<String> keywords = extractKeywords(goalTitle, goalDescription);
            return searchVideosByKeywords(keywords, maxResults);
        } catch (Exception e) {
            logger.error("Error searching videos by goal", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<YouTubeVideoDTO> searchVideosByKeywords(List<String> keywords, int maxResults) {
        if (keywords == null || keywords.isEmpty()) {
            logger.warn("Keywords list is null or empty");
            return new ArrayList<>();
        }

        try {
            YouTube youtubeService = getYouTubeService();

            String searchQuery = String.join(" ", keywords);

            YouTube.Search.List search = youtubeService.search()
                    .list(List.of("id", "snippet"))
                    .setQ(searchQuery)
                    .setType(List.of("video"))
                    .setMaxResults((long) maxResults)
                    .setKey(apiKey);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResults = searchResponse.getItems();

            if (searchResults == null || searchResults.isEmpty()) {
                return new ArrayList<>();
            }

            List<String> videoIds = searchResults.stream()
                    .map(result -> result.getId().getVideoId())
                    .filter(id -> id != null && !id.isEmpty())
                    .collect(Collectors.toList());

            if (videoIds.isEmpty()) {
                return new ArrayList<>();
            }

            YouTube.Videos.List videoRequest = youtubeService.videos()
                    .list(List.of("snippet", "statistics", "contentDetails"))
                    .setId(List.of(String.join(",", videoIds)))
                    .setKey(apiKey);

            VideoListResponse videoResponse = videoRequest.execute();
            List<Video> videoList = videoResponse.getItems();

            if (videoList == null || videoList.isEmpty()) {
                return new ArrayList<>();
            }

            return videoList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error searching videos by keywords", e);
            return new ArrayList<>();
        }
    }

    private YouTubeVideoDTO convertToDTO(SearchResult result) {
        if (result == null || result.getSnippet() == null) {
            return null;
        }

        return YouTubeVideoDTO.builder()
                .videoId(result.getId().getVideoId())
                .title(result.getSnippet().getTitle())
                .description(result.getSnippet().getDescription())
                .thumbnailUrl(result.getSnippet().getThumbnails() != null &&
                        result.getSnippet().getThumbnails().getHigh() != null ?
                        result.getSnippet().getThumbnails().getHigh().getUrl() : null)
                .channelTitle(result.getSnippet().getChannelTitle())
                .publishedAt(result.getSnippet().getPublishedAt() != null ?
                        result.getSnippet().getPublishedAt().toString() : null)
                .build();
    }

    private YouTubeVideoDTO convertToDTO(Video video) {
        if (video == null || video.getSnippet() == null) {
            return null;
        }

        return YouTubeVideoDTO.builder()
                .videoId(video.getId())
                .title(video.getSnippet().getTitle())
                .description(video.getSnippet().getDescription())
                .thumbnailUrl(video.getSnippet().getThumbnails() != null &&
                        video.getSnippet().getThumbnails().getHigh() != null ?
                        video.getSnippet().getThumbnails().getHigh().getUrl() : null)
                .channelTitle(video.getSnippet().getChannelTitle())
                .publishedAt(video.getSnippet().getPublishedAt() != null ?
                        video.getSnippet().getPublishedAt().toString() : null)
                .viewCount(video.getStatistics() != null && video.getStatistics().getViewCount() != null ?
                        video.getStatistics().getViewCount().longValue() : null)
                .likeCount(video.getStatistics() != null && video.getStatistics().getLikeCount() != null ?
                        video.getStatistics().getLikeCount().longValue() : null)
                .duration(video.getContentDetails() != null ?
                        video.getContentDetails().getDuration() : null)
                .url("https://www.youtube.com/watch?v=" + video.getId())
                .build();
    }

    private List<String> extractKeywords(String goalTitle, String goalDescription) {
        String combinedText = (goalTitle + " " + goalDescription).toLowerCase();
        return Arrays.stream(combinedText.split("\\s+"))
                .filter(word -> word.length() > 2) // Filter out very short words
                .distinct() // Remove duplicates
                .collect(Collectors.toList());
    }
} 