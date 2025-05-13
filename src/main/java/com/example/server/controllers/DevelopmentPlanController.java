package com.example.server.controllers;

import dto.DevelopmentPlanDTO;
import com.example.server.services.DevelopmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class DevelopmentPlanController {

    @Autowired
    private DevelopmentPlanService developmentPlanService;

    @PostMapping
    public ResponseEntity<DevelopmentPlanDTO> createPlan(
            @RequestBody DevelopmentPlanDTO planDTO,
            Authentication authentication) {
        // Получаем ID пользователя из аутентификации
        Long userId = getUserIdFromAuthentication(authentication);
        planDTO.setUserId(userId);
        return ResponseEntity.ok(developmentPlanService.createPlan(planDTO));
    }

    @GetMapping
    public ResponseEntity<List<DevelopmentPlanDTO>> getUserPlans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(developmentPlanService.getUserPlans(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentPlanDTO> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(developmentPlanService.getPlan(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentPlanDTO> updatePlan(
            @PathVariable Long id,
            @RequestBody DevelopmentPlanDTO planDTO) {
        return ResponseEntity.ok(developmentPlanService.updatePlan(id, planDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        developmentPlanService.deletePlan(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<DevelopmentPlanDTO> completePlan(@PathVariable Long id) {
        return ResponseEntity.ok(developmentPlanService.completePlan(id));
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        // Здесь нужно реализовать получение ID пользователя из аутентификации
        return 1L; // Временное решение
    }
} 