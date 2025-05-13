package dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GoalDTO {
    private Long id;
    private String title;
    private String description;
    private String type; // BINARY, TIME_BASED, HABIT, QUANTITATIVE
    private Long categoryId;
    private Long userId;
    private LocalDateTime deadline;
    private Boolean isCompleted;
    
    // Для количественных целей
    private Double targetValue;
    private Double currentValue;
    private String measureUnit;
    
    // Для временных целей
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    // Для привычек
    private String frequency; // DAILY, WEEKLY, MONTHLY
    private Integer streak;
    
    // Для бинарных целей
    private Boolean isAchieved;
} 