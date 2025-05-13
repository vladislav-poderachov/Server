package com.example.server.services.impl;

import dto.DevelopmentPlanDTO;
import com.example.server.entities.DevelopmentPlan;
import com.example.server.entities.Goal;
import com.example.server.entities.User;
import com.example.server.repositories.DevelopmentPlanRepository;
import com.example.server.repositories.GoalRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.services.DevelopmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevelopmentPlanServiceImpl implements DevelopmentPlanService {

    @Autowired
    private DevelopmentPlanRepository developmentPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Override
    @Transactional
    public DevelopmentPlanDTO createPlan(DevelopmentPlanDTO planDTO) {
        User user = userRepository.findById(planDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DevelopmentPlan plan = new DevelopmentPlan();
        plan.setTitle(planDTO.getTitle());
        plan.setDescription(planDTO.getDescription());
        plan.setUser(user);
        plan.setStartDate(planDTO.getStartDate());
        plan.setEndDate(planDTO.getEndDate());
        plan.setNotificationsEnabled(planDTO.isNotificationsEnabled());
        plan.setNotificationTime(planDTO.getNotificationTime());

        if (planDTO.getGoalIds() != null) {
            List<Goal> goals = goalRepository.findAllById(planDTO.getGoalIds());
            plan.setGoals(goals);
        }

        DevelopmentPlan savedPlan = developmentPlanRepository.save(plan);
        return convertToDTO(savedPlan);
    }

    @Override
    public List<DevelopmentPlanDTO> getUserPlans(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return developmentPlanRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DevelopmentPlanDTO getPlan(Long id) {
        DevelopmentPlan plan = developmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        return convertToDTO(plan);
    }

    @Override
    @Transactional
    public DevelopmentPlanDTO updatePlan(Long id, DevelopmentPlanDTO planDTO) {
        DevelopmentPlan plan = developmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setTitle(planDTO.getTitle());
        plan.setDescription(planDTO.getDescription());
        plan.setStartDate(planDTO.getStartDate());
        plan.setEndDate(planDTO.getEndDate());
        plan.setNotificationsEnabled(planDTO.isNotificationsEnabled());
        plan.setNotificationTime(planDTO.getNotificationTime());

        if (planDTO.getGoalIds() != null) {
            List<Goal> goals = goalRepository.findAllById(planDTO.getGoalIds());
            plan.setGoals(goals);
        }

        DevelopmentPlan updatedPlan = developmentPlanRepository.save(plan);
        return convertToDTO(updatedPlan);
    }

    @Override
    @Transactional
    public void deletePlan(Long id) {
        developmentPlanRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DevelopmentPlanDTO completePlan(Long id) {
        DevelopmentPlan plan = developmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        plan.setCompleted(true);
        plan.setCompletedAt(LocalDateTime.now());
        
        DevelopmentPlan completedPlan = developmentPlanRepository.save(plan);
        return convertToDTO(completedPlan);
    }

    private DevelopmentPlanDTO convertToDTO(DevelopmentPlan plan) {
        DevelopmentPlanDTO dto = new DevelopmentPlanDTO();
        dto.setId(plan.getId());
        dto.setTitle(plan.getTitle());
        dto.setDescription(plan.getDescription());
        dto.setUserId(plan.getUser().getId());
        dto.setStartDate(plan.getStartDate());
        dto.setEndDate(plan.getEndDate());
        dto.setNotificationsEnabled(plan.isNotificationsEnabled());
        dto.setNotificationTime(plan.getNotificationTime());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setUpdatedAt(plan.getUpdatedAt());
        dto.setProgress(plan.getProgress());
        dto.setCompleted(plan.isCompleted());
        dto.setCompletedAt(plan.getCompletedAt());
        
        if (plan.getGoals() != null) {
            dto.setGoalIds(plan.getGoals().stream()
                    .map(Goal::getId)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
} 