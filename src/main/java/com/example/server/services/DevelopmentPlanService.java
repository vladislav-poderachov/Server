package com.example.server.services;

import dto.DevelopmentPlanDTO;
import java.util.List;

public interface DevelopmentPlanService {
    DevelopmentPlanDTO createPlan(DevelopmentPlanDTO planDTO);
    List<DevelopmentPlanDTO> getUserPlans(Long userId);
    DevelopmentPlanDTO getPlan(Long id);
    DevelopmentPlanDTO updatePlan(Long id, DevelopmentPlanDTO planDTO);
    void deletePlan(Long id);
    DevelopmentPlanDTO completePlan(Long id);
} 