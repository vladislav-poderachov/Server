package com.example.server.services.impl;

import com.example.server.entities.*;
import com.example.server.repositories.*;
import dto.PublicationDTO;
import dto.CommentDTO;
import com.example.server.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Override
    @Transactional
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        User author = userRepository.findById(publicationDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Publication publication = new Publication();
        publication.setTitle(publicationDTO.getTitle());
        publication.setContent(publicationDTO.getContent());
        publication.setAuthor(author);

        if (publicationDTO.getPhotoIds() != null) {
            List<Photo> photos = photoRepository.findAllById(publicationDTO.getPhotoIds());
            publication.setPhotos(photos);
        }

        if (publicationDTO.getGoalIds() != null) {
            List<Goal> goals = goalRepository.findAllById(publicationDTO.getGoalIds());
            publication.setGoals(goals);
        }

        Publication savedPublication = publicationRepository.save(publication);
        return convertToDTO(savedPublication);
    }

    @Override
    public List<PublicationDTO> getAllPublications() {
        return publicationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublicationDTO getPublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        return convertToDTO(publication);
    }

    @Override
    @Transactional
    public PublicationDTO updatePublication(Long id, PublicationDTO publicationDTO) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        publication.setTitle(publicationDTO.getTitle());
        publication.setContent(publicationDTO.getContent());

        if (publicationDTO.getPhotoIds() != null) {
            List<Photo> photos = photoRepository.findAllById(publicationDTO.getPhotoIds());
            publication.setPhotos(photos);
        }

        if (publicationDTO.getGoalIds() != null) {
            List<Goal> goals = goalRepository.findAllById(publicationDTO.getGoalIds());
            publication.setGoals(goals);
        }

        Publication updatedPublication = publicationRepository.save(publication);
        return convertToDTO(updatedPublication);
    }

    @Override
    @Transactional
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CommentDTO addComment(Long publicationId, CommentDTO commentDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        User author = userRepository.findById(commentDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAuthor(author);
        comment.setPublication(publication);

        Comment savedComment = commentRepository.save(comment);
        return convertToCommentDTO(savedComment);
    }

    @Override
    public List<PublicationDTO> getUserPublications(Long userId) {
        return publicationRepository.findByAuthorId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PublicationDTO> getCategoryPublications(Long categoryId) {
        return publicationRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void likePublication(Long userId, Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Публикация не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        
        publication.addLike(user);
        publicationRepository.save(publication);
    }

    @Override
    @Transactional
    public void unlikePublication(Long userId, Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Публикация не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        
        publication.removeLike(user);
        publicationRepository.save(publication);
    }

    private PublicationDTO convertToDTO(Publication publication) {
        PublicationDTO dto = new PublicationDTO();
        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setContent(publication.getContent());
        dto.setAuthorId(publication.getAuthor().getId());
        dto.setCreatedAt(publication.getCreatedAt());
        dto.setUpdatedAt(publication.getUpdatedAt());
        
        if (publication.getPhotos() != null) {
            dto.setPhotoIds(publication.getPhotos().stream()
                    .map(Photo::getId)
                    .collect(Collectors.toList()));
        }
        
        if (publication.getComments() != null) {
            dto.setCommentIds(publication.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList()));
        }
        
        if (publication.getGoals() != null) {
            dto.setGoalIds(publication.getGoals().stream()
                    .map(Goal::getId)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthorId(comment.getAuthor().getId());
        dto.setPublicationId(comment.getPublication().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    private PublicationDTO mapToDTO(Publication publication) {
        PublicationDTO dto = new PublicationDTO();
        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setContent(publication.getContent());
        dto.setAuthorId(publication.getAuthor().getId());
        dto.setCategoryId(publication.getCategory().getId());
        dto.setCreatedAt(publication.getCreatedAt());
        dto.setUpdatedAt(publication.getUpdatedAt());
        return dto;
    }
} 