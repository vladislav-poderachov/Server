package com.example.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.server.enums.GoalType;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BINARY")
public class BinaryGoal extends Goal {
    private boolean isCompleted;
    private LocalDateTime completionDate;
    private String completionNotes;
    
    public BinaryGoal() {
        setType(GoalType.BINARY);
    }
} 