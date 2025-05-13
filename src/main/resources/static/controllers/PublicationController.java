package controllers;

import dto.PublicationDTO;
import dto.CommentDTO;
import services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public ResponseEntity<PublicationDTO> createPublication(@RequestBody PublicationDTO publicationDTO) {
        return ResponseEntity.ok(publicationService.createPublication(publicationDTO));
    }

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAllPublications() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@PathVariable Long id, @RequestBody PublicationDTO publicationDTO) {
        return ResponseEntity.ok(publicationService.updatePublication(id, publicationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{publicationId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long publicationId, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(publicationService.addComment(publicationId, commentDTO));
    }
} 