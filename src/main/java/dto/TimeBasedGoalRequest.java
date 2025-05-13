package dto;

import com.example.server.enums.GoalCategory;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeBasedGoalRequest {
    private String title;
    private String description;
    private GoalCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime targetTime;
    private int allowedDeviationMinutes;
    private boolean flexible;
} 