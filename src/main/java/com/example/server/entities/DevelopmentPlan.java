package com.example.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "development_plans")
public class DevelopmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    @Column(nullable = false)
    private LocalDateTime endDate;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "plan_goals",
        joinColumns = @JoinColumn(name = "plan_id"),
        inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private List<Goal> goals = new ArrayList<>();
    
    @Column(nullable = false)
    private boolean notificationsEnabled = true;
    
    private String notificationTime; // Время для отправки уведомлений (например, "09:00")
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private double progress = 0.0;
    
    @Column(nullable = false)
    private boolean isCompleted = false;
    
    private LocalDateTime completedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public void addGoal(Goal goal) {
        goals.add(goal);
        updateProgress();
    }
    
    public void removeGoal(Goal goal) {
        goals.remove(goal);
        updateProgress();
    }
    
    public void updateProgress() {
        if (goals.isEmpty()) {
            progress = 0.0;
            return;
        }
        
        double totalProgress = goals.stream()
                .mapToDouble(Goal::getCurrentValue)
                .sum();
        double totalTarget = goals.stream()
                .mapToDouble(Goal::getTargetValue)
                .sum();
        
        progress = (totalProgress / totalTarget) * 100;
        
        if (progress >= 100 && !isCompleted) {
            isCompleted = true;
            completedAt = LocalDateTime.now();
        }
    }
} 