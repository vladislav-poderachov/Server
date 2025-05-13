package dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DevelopmentPlanDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> goalIds;
    private boolean notificationsEnabled;
    private String notificationTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double progress;
    private boolean isCompleted;
    private LocalDateTime completedAt;
} 