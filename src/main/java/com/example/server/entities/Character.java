package com.example.server.entities.visualConditions;

import com.example.server.entities.Goal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Базовые характеристики
    private Integer health;         // Здоровье
    private Integer education;      // Образование
    private Integer career;         // Карьера
    private Integer personal;       // Личное развитие
    private Integer habits;         // Привычки
    private Integer finance;        // Финансы
    private Integer relationships;  // Отношения
    private Integer lifestyle;      // Образ жизни

    // Внешний вид (пути к 3D моделям или конфигурация)
    private String modelPath;      // Путь к основной 3D модели
    private String texturePath;    // Путь к текстурам
    private String animationPath;  // Путь к анимациям

    // Дополнительные параметры
    private Integer level;         // Уровень персонажа
    private Integer experience;    // Опыт
    private String title;          // Титул/ранг
    private String description;    // Описание персонажа

    // JSON конфигурация для дополнительных параметров
    @Column(columnDefinition = "TEXT")
    private String configuration;  // JSON с дополнительными параметрами

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Методы для расчета характеристик на основе целей
    public void updateStatsFromGoals(List<Goal> completedGoals) {
        for (Goal goal : completedGoals) {
            // Базовый опыт за цель
            int baseExp = 100;
            
            // Множитель опыта в зависимости от приоритета
            double priorityMultiplier = switch (goal.getPriority()) {
                case LOW -> 1.0;
                case MEDIUM -> 1.5;
                case HIGH -> 2.0;
                case URGENT -> 3.0;
            };
            
            // Множитель опыта в зависимости от частоты обновления
            double frequencyMultiplier = switch (goal.getUpdateFrequency()) {
                case DAILY -> 1.0;
                case WEEKLY -> 2.0;
                case MONTHLY -> 3.0;
                case CUSTOM -> 1.5;
            };
            
            // Начисление опыта
            int expGain = (int) (baseExp * priorityMultiplier * frequencyMultiplier);
            this.experience += expGain;
            
            // Обновление атрибутов в зависимости от категории цели
            switch (goal.getCategory()) {
                case HEALTH -> this.health += (int) (5 * priorityMultiplier);
                case EDUCATION -> this.education += (int) (5 * priorityMultiplier);
                case CAREER -> this.career += (int) (5 * priorityMultiplier);
                case PERSONAL -> this.personal += (int) (5 * priorityMultiplier);
                case HABITS -> this.habits += (int) (5 * priorityMultiplier);
                case FINANCE -> this.finance += (int) (5 * priorityMultiplier);
                case RELATIONSHIPS -> this.relationships += (int) (5 * priorityMultiplier);
                case LIFESTYLE -> this.lifestyle += (int) (5 * priorityMultiplier);
            }
        }
        
        // Пересчет уровня после обновления опыта
        calculateLevel();
    }

    // Методы для расчета уровня
    public void calculateLevel() {
        // Формула: level = 1 + sqrt(experience / 1000)
        this.level = 1 + (int) Math.sqrt(this.experience / 1000.0);
        
        // Обновление титула в зависимости от уровня
        this.title = "Уровень " + this.level;
    }
}
