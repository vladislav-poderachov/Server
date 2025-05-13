package entities;

import entities.Goal;
import entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_goals")
public class UserGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;
    
    @Column(nullable = false)
    private LocalDateTime assignedAt;
    
    private LocalDateTime completedAt;
    
    private double progress;
    
    @Column(length = 1000)
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
        progress = 0.0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        completedAt = LocalDateTime.now();
    }
}
