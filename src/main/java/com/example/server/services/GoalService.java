package services;

import dto.*;
import dto.BinaryGoalRequest;
import dto.HabitGoalRequest;
import dto.QuantitativeGoalRequest;
import dto.TimeBasedGoalRequest;
import entities.Goal;
import enums.GoalCategory;
import enums.GoalType;
import java.util.List;

public interface GoalService {
    // Общие методы
    Goal getGoal(Long goalId);
    List<Goal> getUserGoals(Long userId);
    void deleteGoal(Long goalId);
    
    // Методы для количественных целей
    Goal createQuantitativeGoal(Long userId, QuantitativeGoalRequest request);
    Goal updateQuantitativeGoal(Long goalId, QuantitativeGoalRequest request);
    Goal updateQuantitativeProgress(Long goalId, double value, double time);
    
    // Методы для целей по привычкам
    Goal createHabitGoal(Long userId, HabitGoalRequest request);
    Goal updateHabitGoal(Long goalId, HabitGoalRequest request);
    Goal updateHabitProgress(Long goalId, boolean isSuccess, String failureReason);
    
    // Методы для временных целей
    Goal createTimeBasedGoal(Long userId, TimeBasedGoalRequest request);
    Goal updateTimeBasedGoal(Long goalId, TimeBasedGoalRequest request);
    Goal updateTimeBasedProgress(Long goalId, boolean isSuccess);
    
    // Методы для бинарных целей
    Goal createBinaryGoal(Long userId, BinaryGoalRequest request);
    Goal updateBinaryGoal(Long goalId, BinaryGoalRequest request);
    Goal completeBinaryGoal(Long goalId, String completionNotes);
    
    // Методы для работы с категориями
    List<Goal> getGoalsByCategory(Long userId, GoalCategory category);
    List<Goal> getGoalsByType(Long userId, GoalType type);
    
    // Методы для аналитики
    GoalProgressMetrics getGoalProgressMetrics(Long goalId);
    List<GoalProgressMetrics> getUserGoalsProgressMetrics(Long userId);
}
