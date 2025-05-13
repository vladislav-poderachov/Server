package com.example.server.controllers;

import com.example.server.config.SecurityUser;
import dto.CommentRequest;
import dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.server.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.createComment(securityUser.getUser().getId(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<List<CommentResponse>> getPublicationComments(@PathVariable Long publicationId) {
        return ResponseEntity.ok(commentService.getPublicationComments(publicationId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getUserComments(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getUserComments(userId));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeComment(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long id) {
        commentService.likeComment(securityUser.getUser().getId(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikeComment(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long id) {
        commentService.unlikeComment(securityUser.getUser().getId(), id);
        return ResponseEntity.ok().build();
    }
} 