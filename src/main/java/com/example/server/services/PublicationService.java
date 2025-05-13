package com.example.server.services;

import dto.PublicationDTO;
import dto.CommentDTO;

import java.util.List;

public interface PublicationService {
    PublicationDTO createPublication(PublicationDTO publicationDTO);
    List<PublicationDTO> getAllPublications();
    PublicationDTO getPublication(Long id);
    PublicationDTO updatePublication(Long id, PublicationDTO publicationDTO);
    void deletePublication(Long id);
    CommentDTO addComment(Long publicationId, CommentDTO commentDTO);
    List<PublicationDTO> getUserPublications(Long userId);
    List<PublicationDTO> getCategoryPublications(Long categoryId);
    void likePublication(Long userId, Long publicationId);
    void unlikePublication(Long userId, Long publicationId);
}

