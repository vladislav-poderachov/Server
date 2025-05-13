package dto;

import com.example.server.enums.GoalPriority;
import com.example.server.enums.GoalStatus;
import com.example.server.enums.GoalUpdateFrequency;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class GoalResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String measureUnit;
    private double targetValue;
    private double currentValue;
    private GoalStatus status;
    private GoalUpdateFrequency updateFrequency;
    private GoalPriority priority;
    private String category;
    private boolean remindersEnabled;
    private LocalTime reminderTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double progress;
} 