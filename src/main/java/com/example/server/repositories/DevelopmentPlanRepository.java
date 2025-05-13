package com.example.server.repositories;

import com.example.server.entities.DevelopmentPlan;
import com.example.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevelopmentPlanRepository extends JpaRepository<DevelopmentPlan, Long> {
    List<DevelopmentPlan> findByUser(User user);
} 