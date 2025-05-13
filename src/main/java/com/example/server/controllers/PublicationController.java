package com.example.server.controllers;

import com.example.server.config.SecurityUser;
import dto.PublicationDTO;
import com.example.server.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    @PostMapping
    public ResponseEntity<PublicationDTO> createPublication(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody PublicationDTO request) {
        request.setAuthorId(securityUser.getUser().getId());
        return ResponseEntity.ok(publicationService.createPublication(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(
            @PathVariable Long id,
            @RequestBody PublicationDTO request) {
        return ResponseEntity.ok(publicationService.updatePublication(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PublicationDTO>> getUserPublications(@PathVariable Long userId) {
        return ResponseEntity.ok(publicationService.getUserPublications(userId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PublicationDTO>> getCategoryPublications(@PathVariable Long categoryId) {
        return ResponseEntity.ok(publicationService.getCategoryPublications(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAllPublications() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePublication(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long id) {
        publicationService.likePublication(securityUser.getUser().getId(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikePublication(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long id) {
        publicationService.unlikePublication(securityUser.getUser().getId(), id);
        return ResponseEntity.ok().build();
    }
} 