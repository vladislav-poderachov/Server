package com.example.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.server.enums.GoalType;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "habit_goals")
public class HabitGoal extends Goal {
    @Column(nullable = false)
    private int targetFrequency;
    
    @Column(nullable = false)
    private String frequencyUnit;
    
    private int currentFrequency;
    
    private boolean success;
    
    private String failureReason;
    
    private int streakDays;
    
    private int longestStreak;
    
    private int totalSuccessDays;
    
    private int totalFailureDays;

    public HabitGoal() {
        setType(GoalType.HABIT);
    }
} 