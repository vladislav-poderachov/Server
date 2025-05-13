package com.example.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.server.enums.GoalType;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time_based_goals")
public class TimeBasedGoal extends Goal {
    @Column(nullable = false)
    private LocalDateTime targetTime;
    
    @Column(nullable = false)
    private int allowedDeviationMinutes;
    
    private boolean flexible;
    
    private int successCount;
    
    private int failureCount;

    public TimeBasedGoal() {
        setType(GoalType.TIME_BASED);
    }
} 