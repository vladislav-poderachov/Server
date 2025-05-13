package com.example.server.services.impl;

import dto.CommentRequest;
import dto.CommentResponse;
import com.example.server.entities.Comment;
import com.example.server.entities.Publication;
import com.example.server.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.server.repositories.CommentRepository;
import com.example.server.repositories.PublicationRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    @Override
    @Transactional
    public CommentResponse createComment(Long userId, CommentRequest request) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        
        Publication publication = publicationRepository.findById(request.getPublicationId())
                .orElseThrow(() -> new RuntimeException("Публикация не найдена"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setAuthor(author);
        comment.setPublication(publication);

        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Родительский комментарий не найден"));
            comment.setParentComment(parentComment);
        }

        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        comment.setContent(request.getContent());

        Comment updatedComment = commentRepository.save(comment);
        return mapToResponse(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        return mapToResponse(comment);
    }

    @Override
    public List<CommentResponse> getPublicationComments(Long publicationId) {
        return commentRepository.findByPublicationIdAndParentCommentIsNull(publicationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getUserComments(Long userId) {
        return commentRepository.findByAuthorId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void likeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
            commentRepository.save(comment);
        }
    }

    @Override
    @Transactional
    public void unlikeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        comment.getLikes().remove(user);
        commentRepository.save(comment);
    }

    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorLogin(comment.getAuthor().getLogin())
                .publicationId(comment.getPublication().getId())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .replies(comment.getReplies().stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList()))
                .likesCount(comment.getLikes().size())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
} 