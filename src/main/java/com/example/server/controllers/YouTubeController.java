package com.example.server.controllers;

import dto.YouTubeVideoDTO;
import com.example.server.services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/youtube")
@Validated
public class YouTubeController {
    private static final Logger logger = LoggerFactory.getLogger(YouTubeController.class);
    private final YouTubeService youTubeService;

    @Autowired
    public YouTubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/search/goal")
    public ResponseEntity<List<YouTubeVideoDTO>> searchVideosByGoal(
            @RequestParam @NotBlank(message = "Goal title cannot be empty") String goalTitle,
            @RequestParam @NotBlank(message = "Goal description cannot be empty") String goalDescription,
            @RequestParam(defaultValue = "5") @Min(1) @Min(50) int maxResults) {
        try {
            List<YouTubeVideoDTO> videos = youTubeService.searchVideosByGoal(goalTitle, goalDescription, maxResults);
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            logger.error("Error searching videos by goal", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search/keywords")
    public ResponseEntity<List<YouTubeVideoDTO>> searchVideosByKeywords(
            @RequestParam @Size(min = 1, message = "At least one keyword is required") List<@NotBlank String> keywords,
            @RequestParam(defaultValue = "5") @Min(1) @Min(50) int maxResults) {
        try {
            List<YouTubeVideoDTO> videos = youTubeService.searchVideosByKeywords(keywords, maxResults);
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            logger.error("Error searching videos by keywords", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/videos/{videoId}")
    public ResponseEntity<YouTubeVideoDTO> getVideoDetails(
            @PathVariable @NotBlank(message = "Video ID cannot be empty") String videoId) {
        try {
            YouTubeVideoDTO video = youTubeService.getVideoDetails(videoId);
            return video != null ? ResponseEntity.ok(video) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error getting video details", e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 