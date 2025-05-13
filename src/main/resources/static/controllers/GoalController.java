package controllers;

import dto.*;
import entities.Goal;
import enums.GoalCategory;
import enums.GoalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import services.GoalService;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // Общие эндпоинты
    @GetMapping("/{goalId}")
    public ResponseEntity<Goal> getGoal(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.getGoal(goalId));
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getUserGoals(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.getUserGoals(userId));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.ok().build();
    }

    // Эндпоинты для количественных целей
    @PostMapping("/quantitative")
    public ResponseEntity<Goal> createQuantitativeGoal(
            Authentication authentication,
            @RequestBody QuantitativeGoalRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.createQuantitativeGoal(userId, request));
    }

    @PutMapping("/quantitative/{goalId}")
    public ResponseEntity<Goal> updateQuantitativeGoal(
            @PathVariable Long goalId,
            @RequestBody QuantitativeGoalRequest request) {
        return ResponseEntity.ok(goalService.updateQuantitativeGoal(goalId, request));
    }

    @PutMapping("/quantitative/{goalId}/progress")
    public ResponseEntity<Goal> updateQuantitativeProgress(
            @PathVariable Long goalId,
            @RequestParam double value,
            @RequestParam double time) {
        return ResponseEntity.ok(goalService.updateQuantitativeProgress(goalId, value, time));
    }

    // Эндпоинты для целей по привычкам
    @PostMapping("/habit")
    public ResponseEntity<Goal> createHabitGoal(
            Authentication authentication,
            @RequestBody HabitGoalRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.createHabitGoal(userId, request));
    }

    @PutMapping("/habit/{goalId}")
    public ResponseEntity<Goal> updateHabitGoal(
            @PathVariable Long goalId,
            @RequestBody HabitGoalRequest request) {
        return ResponseEntity.ok(goalService.updateHabitGoal(goalId, request));
    }

    @PutMapping("/habit/{goalId}/progress")
    public ResponseEntity<Goal> updateHabitProgress(
            @PathVariable Long goalId,
            @RequestParam boolean isSuccess,
            @RequestParam(required = false) String failureReason) {
        return ResponseEntity.ok(goalService.updateHabitProgress(goalId, isSuccess, failureReason));
    }

    // Эндпоинты для временных целей
    @PostMapping("/time-based")
    public ResponseEntity<Goal> createTimeBasedGoal(
            Authentication authentication,
            @RequestBody TimeBasedGoalRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.createTimeBasedGoal(userId, request));
    }

    @PutMapping("/time-based/{goalId}")
    public ResponseEntity<Goal> updateTimeBasedGoal(
            @PathVariable Long goalId,
            @RequestBody TimeBasedGoalRequest request) {
        return ResponseEntity.ok(goalService.updateTimeBasedGoal(goalId, request));
    }

    @PutMapping("/time-based/{goalId}/progress")
    public ResponseEntity<Goal> updateTimeBasedProgress(
            @PathVariable Long goalId,
            @RequestParam boolean isSuccess) {
        return ResponseEntity.ok(goalService.updateTimeBasedProgress(goalId, isSuccess));
    }

    // Эндпоинты для бинарных целей
    @PostMapping("/binary")
    public ResponseEntity<Goal> createBinaryGoal(
            Authentication authentication,
            @RequestBody BinaryGoalRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.createBinaryGoal(userId, request));
    }

    @PutMapping("/binary/{goalId}")
    public ResponseEntity<Goal> updateBinaryGoal(
            @PathVariable Long goalId,
            @RequestBody BinaryGoalRequest request) {
        return ResponseEntity.ok(goalService.updateBinaryGoal(goalId, request));
    }

    @PutMapping("/binary/{goalId}/complete")
    public ResponseEntity<Goal> completeBinaryGoal(
            @PathVariable Long goalId,
            @RequestParam String completionNotes) {
        return ResponseEntity.ok(goalService.completeBinaryGoal(goalId, completionNotes));
    }

    // Эндпоинты для работы с категориями
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Goal>> getGoalsByCategory(
            Authentication authentication,
            @PathVariable GoalCategory category) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.getGoalsByCategory(userId, category));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Goal>> getGoalsByType(
            Authentication authentication,
            @PathVariable GoalType type) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.getGoalsByType(userId, type));
    }

    // Эндпоинты для аналитики
    @GetMapping("/{goalId}/metrics")
    public ResponseEntity<GoalProgressMetrics> getGoalProgressMetrics(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.getGoalProgressMetrics(goalId));
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<GoalProgressMetrics>> getUserGoalsProgressMetrics(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(goalService.getUserGoalsProgressMetrics(userId));
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        // Здесь нужно реализовать получение ID пользователя из аутентификации
        return 1L; // Временное решение
    }
} 