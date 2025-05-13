package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.IntegerList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publications")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
        name = "publication_goals",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private List<Goal> goals;

    @ManyToMany
    @JoinTable(
        name = "publication_likes",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPublication(this);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setPublication(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPublication(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPublication(null);
    }

    public void addLike(User user) {
        if (!likes.contains(user)) {
            likes.add(user);
            likesCount++;
        }
    }

    public void removeLike(User user) {
        if (likes.remove(user)) {
            likesCount--;
        }
    }


}