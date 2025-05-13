package controllers;

import dto.YouTubeVideoDTO;
import services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/search")
    public ResponseEntity<List<YouTubeVideoDTO>> searchVideos(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int maxResults) {
        return ResponseEntity.ok(youTubeService.searchVideos(query, maxResults));
    }

    @GetMapping("/videos/{videoId}")
    public ResponseEntity<YouTubeVideoDTO> getVideoDetails(@PathVariable String videoId) {
        return ResponseEntity.ok(youTubeService.getVideoDetails(videoId));
    }
} 