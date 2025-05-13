package entities;

import enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goals")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "goal_type")
public abstract class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;

    private String customMeasureUnit;

    @Column(nullable = false)
    private double targetValue;

    private double currentValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status = GoalStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalUpdateFrequency updateFrequency = GoalUpdateFrequency.DAILY;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalPriority priority = GoalPriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalCategory category;

    private boolean remindersEnabled = false;

    private LocalTime reminderTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
