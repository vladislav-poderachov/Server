package com.example.server.services.impl;

import com.example.server.entities.*;
import com.example.server.repositories.GoalRepository;
import com.example.server.repositories.MeasureUnitRepository;
import com.example.server.repositories.UserGoalRepository;
import com.example.server.repositories.UserRepository;
import dto.*;
import com.example.server.enums.GoalCategory;
import com.example.server.enums.GoalStatus;
import com.example.server.enums.GoalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.services.GoalService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGoalRepository userGoalRepository;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    @Override
    public Goal getGoal(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    @Override
    public List<Goal> getUserGoals(Long userId) {
        return userGoalRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")))
                .stream()
                .map(UserGoal::getGoal)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGoal(Long goalId) {
        Goal goal = getGoal(goalId);
        userGoalRepository.deleteByGoal(goal);
        goalRepository.delete(goal);
    }

    @Override
    public Goal createQuantitativeGoal(Long userId, QuantitativeGoalRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        QuantitativeGoal goal = new QuantitativeGoal();
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setType(GoalType.QUANTITATIVE);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setStartDate(LocalDateTime.now());
        goal.setEndDate(request.getEndDate());
        goal.setTargetValue(request.getTargetValue());
        goal.setCurrentValue(0.0);
        goal.setTimeTarget(request.getTimeTarget());
        goal.setCurrentTimeValue(0.0);
        goal.setValueUnit(request.getValueUnit());

        Goal savedGoal = goalRepository.save(goal);
        UserGoal userGoal = new UserGoal();
        userGoal.setUser(user);
        userGoal.setGoal(savedGoal);
        userGoalRepository.save(userGoal);

        return savedGoal;
    }

    @Override
    public Goal updateQuantitativeGoal(Long goalId, QuantitativeGoalRequest request) {
        QuantitativeGoal goal = (QuantitativeGoal) getGoal(goalId);
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setEndDate(request.getEndDate());
        goal.setTargetValue(request.getTargetValue());
        goal.setTimeTarget(request.getTimeTarget());
        goal.setValueUnit(request.getValueUnit());
        return goalRepository.save(goal);
    }

    @Override
    public Goal updateQuantitativeProgress(Long goalId, double value, double time) {
        QuantitativeGoal goal = (QuantitativeGoal) getGoal(goalId);
        goal.setCurrentValue(value);
        goal.setCurrentTimeValue(time);
        
        double progress = calculateProgress(goal);
        UserGoal userGoal = userGoalRepository.findByGoal(goal)
                .orElseThrow(() -> new RuntimeException("User goal not found"));
        userGoal.setProgress(progress);
        
        if (progress >= 100) {
            goal.setStatus(GoalStatus.COMPLETED);
            userGoal.setCompletedAt(LocalDateTime.now());
        }
        
        userGoalRepository.save(userGoal);
        return goalRepository.save(goal);
    }

    @Override
    public Goal createHabitGoal(Long userId, HabitGoalRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HabitGoal goal = new HabitGoal();
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setType(GoalType.HABIT);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setStartDate(LocalDateTime.now());
        goal.setEndDate(request.getEndDate());
        goal.setTargetFrequency(request.getTargetFrequency());
        goal.setCurrentFrequency(0);
        goal.setStreakDays(0);
        goal.setLongestStreak(0);
        goal.setTotalSuccessDays(0);
        goal.setTotalFailureDays(0);

        Goal savedGoal = goalRepository.save(goal);
        UserGoal userGoal = new UserGoal();
        userGoal.setUser(user);
        userGoal.setGoal(savedGoal);
        userGoalRepository.save(userGoal);

        return savedGoal;
    }

    @Override
    public Goal updateHabitGoal(Long goalId, HabitGoalRequest request) {
        HabitGoal goal = (HabitGoal) getGoal(goalId);
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setEndDate(request.getEndDate());
        goal.setTargetFrequency(request.getTargetFrequency());
        return goalRepository.save(goal);
    }

    @Override
    public Goal updateHabitProgress(Long goalId, boolean isSuccess, String failureReason) {
        HabitGoal goal = (HabitGoal) getGoal(goalId);
        
        if (isSuccess) {
            goal.setStreakDays(goal.getStreakDays() + 1);
            goal.setTotalSuccessDays(goal.getTotalSuccessDays() + 1);
            goal.setLongestStreak(Math.max(goal.getLongestStreak(), goal.getStreakDays()));
        } else {
            goal.setStreakDays(0);
            goal.setTotalFailureDays(goal.getTotalFailureDays() + 1);
        }
        
        double progress = calculateHabitProgress(goal);
        UserGoal userGoal = userGoalRepository.findByGoal(goal)
                .orElseThrow(() -> new RuntimeException("User goal not found"));
        userGoal.setProgress(progress);
        
        if (progress >= 100) {
            goal.setStatus(GoalStatus.COMPLETED);
            userGoal.setCompletedAt(LocalDateTime.now());
        }
        
        userGoalRepository.save(userGoal);
        return goalRepository.save(goal);
    }

    @Override
    public Goal createTimeBasedGoal(Long userId, TimeBasedGoalRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TimeBasedGoal goal = new TimeBasedGoal();
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setType(GoalType.TIME_BASED);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setStartDate(LocalDateTime.now());
        goal.setEndDate(request.getEndDate());
        goal.setSuccessCount(0);
        goal.setFailureCount(0);

        Goal savedGoal = goalRepository.save(goal);
        UserGoal userGoal = new UserGoal();
        userGoal.setUser(user);
        userGoal.setGoal(savedGoal);
        userGoalRepository.save(userGoal);

        return savedGoal;
    }

    @Override
    public Goal updateTimeBasedGoal(Long goalId, TimeBasedGoalRequest request) {
        TimeBasedGoal goal = (TimeBasedGoal) getGoal(goalId);
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setEndDate(request.getEndDate());
        return goalRepository.save(goal);
    }

    @Override
    public Goal updateTimeBasedProgress(Long goalId, boolean isSuccess) {
        TimeBasedGoal goal = (TimeBasedGoal) getGoal(goalId);
        
        if (isSuccess) {
            goal.setSuccessCount(goal.getSuccessCount() + 1);
        } else {
            goal.setFailureCount(goal.getFailureCount() + 1);
        }
        
        double progress = calculateTimeBasedProgress(goal);
        UserGoal userGoal = userGoalRepository.findByGoal(goal)
                .orElseThrow(() -> new RuntimeException("User goal not found"));
        userGoal.setProgress(progress);
        
        if (progress >= 100) {
            goal.setStatus(GoalStatus.COMPLETED);
            userGoal.setCompletedAt(LocalDateTime.now());
        }
        
        userGoalRepository.save(userGoal);
        return goalRepository.save(goal);
    }

    @Override
    public Goal createBinaryGoal(Long userId, BinaryGoalRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BinaryGoal goal = new BinaryGoal();
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setType(GoalType.BINARY);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setStartDate(LocalDateTime.now());
        goal.setEndDate(request.getEndDate());
        goal.setCompleted(false);
        goal.setCompletionNotes(null);

        Goal savedGoal = goalRepository.save(goal);
        UserGoal userGoal = new UserGoal();
        userGoal.setUser(user);
        userGoal.setGoal(savedGoal);
        userGoalRepository.save(userGoal);

        return savedGoal;
    }

    @Override
    public Goal updateBinaryGoal(Long goalId, BinaryGoalRequest request) {
        BinaryGoal goal = (BinaryGoal) getGoal(goalId);
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setCategory(request.getCategory());
        goal.setEndDate(request.getEndDate());
        return goalRepository.save(goal);
    }

    @Override
    public Goal completeBinaryGoal(Long goalId, String completionNotes) {
        BinaryGoal goal = (BinaryGoal) getGoal(goalId);
        goal.setCompleted(true);
        goal.setCompletionNotes(completionNotes);
        goal.setStatus(GoalStatus.COMPLETED);
        
        UserGoal userGoal = userGoalRepository.findByGoal(goal)
                .orElseThrow(() -> new RuntimeException("User goal not found"));
        userGoal.setProgress(100.0);
        userGoal.setCompletedAt(LocalDateTime.now());
        
        userGoalRepository.save(userGoal);
        return goalRepository.save(goal);
    }

    @Override
    public List<Goal> getGoalsByCategory(Long userId, GoalCategory category) {
        return getUserGoals(userId).stream()
                .filter(goal -> goal.getCategory() == category)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goal> getGoalsByType(Long userId, GoalType type) {
        return getUserGoals(userId).stream()
                .filter(goal -> goal.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public GoalProgressMetrics getGoalProgressMetrics(Long goalId) {
        Goal goal = getGoal(goalId);
        UserGoal userGoal = userGoalRepository.findByGoal(goal)
                .orElseThrow(() -> new RuntimeException("User goal not found"));

        GoalProgressMetrics metrics = new GoalProgressMetrics();
        metrics.setGoalId(goalId);
        metrics.setTitle(goal.getTitle());
        metrics.setCategory(goal.getCategory());
        metrics.setType(goal.getType());
        metrics.setStatus(goal.getStatus());
        metrics.setProgress(userGoal.getProgress());
        metrics.setStartDate(goal.getStartDate());
        metrics.setEndDate(goal.getEndDate());
        metrics.setCreatedAt(goal.getCreatedAt());
        metrics.setUpdatedAt(goal.getUpdatedAt());

        // Добавляем специфичные метрики в зависимости от типа цели
        switch (goal.getType()) {
            case QUANTITATIVE -> {
                QuantitativeGoal qGoal = (QuantitativeGoal) goal;
                metrics.setCurrentValue(qGoal.getCurrentValue());
                metrics.setTargetValue(qGoal.getTargetValue());
                metrics.setValueUnit(qGoal.getValueUnit());
                metrics.setTimeProgress(qGoal.getCurrentTimeValue() / qGoal.getTimeTarget() * 100);
            }
            case HABIT -> {
                HabitGoal hGoal = (HabitGoal) goal;
                metrics.setCurrentFrequency(hGoal.getCurrentFrequency());
                metrics.setTargetFrequency(hGoal.getTargetFrequency());
                metrics.setStreakDays(hGoal.getStreakDays());
                metrics.setLongestStreak(hGoal.getLongestStreak());
                metrics.setSuccessRate((hGoal.getTotalSuccessDays() / (double) (hGoal.getTotalSuccessDays() + hGoal.getTotalFailureDays())) * 100);
            }
            case TIME_BASED -> {
                TimeBasedGoal tGoal = (TimeBasedGoal) goal;
                metrics.setSuccessCount(tGoal.getSuccessCount());
                metrics.setFailureCount(tGoal.getFailureCount());
                metrics.setSuccessRate((tGoal.getSuccessCount() / (double) (tGoal.getSuccessCount() + tGoal.getFailureCount())) * 100);
            }
        }

        return metrics;
    }

    @Override
    public List<GoalProgressMetrics> getUserGoalsProgressMetrics(Long userId) {
        return getUserGoals(userId).stream()
                .map(goal -> getGoalProgressMetrics(goal.getId()))
                .collect(Collectors.toList());
    }

    private double calculateProgress(QuantitativeGoal goal) {
        if (goal.getTargetValue() == 0) return 0;
        
        double valueProgress = (goal.getCurrentValue() / goal.getTargetValue()) * 100;
        double timeProgress = (goal.getCurrentTimeValue() / goal.getTimeTarget()) * 100;
        
        // Возвращаем среднее значение прогресса по значению и времени
        return Math.min(100, (valueProgress + timeProgress) / 2);
    }

    private double calculateHabitProgress(HabitGoal goal) {
        if (goal.getTargetFrequency() == 0) return 0;
        
        // Рассчитываем прогресс на основе текущей частоты и целевой частоты
        double frequencyProgress = (goal.getCurrentFrequency() / (double) goal.getTargetFrequency()) * 100;
        
        // Добавляем бонус за серию успешных дней
        double streakBonus = Math.min(20, goal.getStreakDays() * 2); // Максимум 20% бонуса
        
        return Math.min(100, frequencyProgress + streakBonus);
    }

    private double calculateTimeBasedProgress(TimeBasedGoal goal) {
        if (goal.getSuccessCount() + goal.getFailureCount() == 0) return 0;
        
        // Рассчитываем прогресс на основе соотношения успешных и неуспешных попыток
        double successRate = (goal.getSuccessCount() / (double) (goal.getSuccessCount() + goal.getFailureCount())) * 100;
        
        // Добавляем бонус за последовательные успехи
        double consecutiveBonus = Math.min(15, goal.getSuccessCount() * 3); // Максимум 15% бонуса
        
        return Math.min(100, successRate + consecutiveBonus);
    }
} 