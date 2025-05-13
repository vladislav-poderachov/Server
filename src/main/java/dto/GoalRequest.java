package dto;

import com.example.server.enums.GoalPriority;
import com.example.server.enums.GoalUpdateFrequency;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class GoalRequest {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer measureUnitId;
    private String customMeasureUnit;
    private double targetValue;
    private GoalUpdateFrequency updateFrequency;
    private GoalPriority priority;
    private Integer categoryId;
    private boolean remindersEnabled;
    private LocalTime reminderTime;
} 