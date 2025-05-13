package dto;

import com.example.server.enums.GoalCategory;
import com.example.server.enums.GoalStatus;
import com.example.server.enums.GoalType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GoalProgressMetrics {
    private Long goalId;
    private String title;
    private GoalCategory category;
    private GoalType type;
    private GoalStatus status;
    private double progress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Метрики для количественных целей
    private double currentValue;
    private double targetValue;
    private String valueUnit;
    private double timeProgress;

    // Метрики для целей по привычкам
    private int currentFrequency;
    private int targetFrequency;
    private int streakDays;
    private int longestStreak;
    private double successRate;

    // Метрики для временных целей
    private int successCount;
    private int failureCount;

    // Базовые метрики скорости
    private double averageSpeed; // Средняя скорость прогресса в единицах/день
    private double medianSpeed; // Медианная скорость прогресса
    private double minSpeed; // Минимальная скорость
    private double maxSpeed; // Максимальная скорость
    private double speedStandardDeviation; // Стандартное отклонение скорости
    
    // Прогнозные метрики
    private LocalDateTime expectedCompletionDate; // Ожидаемая дата завершения
    private LocalDateTime optimisticCompletionDate; // Оптимистичная дата завершения
    private LocalDateTime pessimisticCompletionDate; // Пессимистичная дата завершения
    private long daysToCompletion; // Дней до завершения
    
    // Метрики стабильности
    private double progressDaysPercentage; // Процент дней с прогрессом
    private int longestProgressStreak; // Самая длинная серия дней с прогрессом
    private int longestNoProgressStreak; // Самая длинная серия дней без прогресса
    
    // Дополнительные метрики
    private double currentProgress; // Текущий прогресс в процентах
    private double remainingProgress; // Оставшийся прогресс в процентах
    private double consistencyScore; // Оценка стабильности прогресса (0-100)
    
    // Метрики для целей с временными рамками
    private boolean isOnTrack;
} 