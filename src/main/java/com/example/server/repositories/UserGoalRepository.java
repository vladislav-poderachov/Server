package com.example.server.repositories;

import com.example.server.entities.Goal;
import com.example.server.entities.User;
import com.example.server.entities.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {
    Optional<UserGoal> findByGoal(Goal goal);
    void deleteByGoal(Goal goal);
    List<UserGoal> findByUser(User user);
} 