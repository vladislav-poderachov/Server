package com.example.server.services;

import dto.CommentRequest;
import dto.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long userId, CommentRequest request);
    CommentResponse updateComment(Long commentId, CommentRequest request);
    void deleteComment(Long commentId);
    CommentResponse getComment(Long commentId);
    List<CommentResponse> getPublicationComments(Long publicationId);
    List<CommentResponse> getUserComments(Long userId);
    void likeComment(Long userId, Long commentId);
    void unlikeComment(Long userId, Long commentId);
} 