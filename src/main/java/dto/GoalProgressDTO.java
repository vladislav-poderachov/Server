package dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GoalProgressDTO {
    private Long id;
    private Long goalId;
    private double progress;
    private String notes;
    private LocalDateTime timestamp;
} 