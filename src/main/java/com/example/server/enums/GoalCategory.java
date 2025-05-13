package com.example.server.enums;

public enum GoalCategory {
    HEALTH("Здоровье", 
        "Цели, связанные со здоровьем и физической активностью. " +
        "Примеры: бег, плавание, отжимания, здоровый сон"),
    
    EDUCATION("Образование", 
        "Цели по обучению и развитию навыков. " +
        "Примеры: изучение языков, программирование, чтение книг"),
    
    CAREER("Карьера", 
        "Профессиональные цели. " +
        "Примеры: повышение квалификации, смена работы, повышение"),
    
    PERSONAL("Личное развитие", 
        "Цели личностного роста. " +
        "Примеры: медитация, самоанализ, развитие soft skills"),
    
    HABITS("Привычки", 
        "Цели по формированию или изменению привычек. " +
        "Примеры: режим дня, питание, физическая активность"),
    
    FINANCE("Финансы", 
        "Финансовые цели. " +
        "Примеры: накопления, инвестиции, сокращение расходов"),
    
    RELATIONSHIPS("Отношения", 
        "Цели в сфере отношений. " +
        "Примеры: общение, семья, друзья"),
    
    LIFESTYLE("Образ жизни", 
        "Цели по изменению образа жизни. " +
        "Примеры: экологичность, минимализм, здоровый образ жизни");
    
    private final String title;
    private final String description;
    
    GoalCategory(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
} 