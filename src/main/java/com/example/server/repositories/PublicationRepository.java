package com.example.server.repositories;

import com.example.server.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByAuthorId(Long authorId);
    List<Publication> findByCategoryId(Long categoryId);
} 