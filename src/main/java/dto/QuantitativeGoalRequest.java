package dto;

import com.example.server.enums.GoalCategory;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QuantitativeGoalRequest {
    private String title;
    private String description;
    private GoalCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double targetValue;
    private String valueUnit;
    private double timeTarget;
    private String timeUnit;
    private Long measureUnitId;
    private String customMeasureUnit;
} 