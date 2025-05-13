package com.example.server.enums;

public enum GoalType {
    QUANTITATIVE("Количественная цель", 
        "Цель с числовым значением и/или временным параметром. " +
        "Примеры: пробежать 30 метров за 10 секунд, прочитать 200 страниц, " +
        "сделать 100 отжиманий"),
    
    HABIT("Привычка", 
        "Цель по формированию или отказу от привычки. " +
        "Примеры: ложиться спать в 8 вечера, бросить курить, " +
        "пить 2 литра воды в день"),
    
    TIME_BASED("Временная цель", 
        "Цель с привязкой к конкретному времени. " +
        "Примеры: вставать в 6 утра, медитировать 20 минут в день"),
    
    BINARY("Бинарная цель", 
        "Цель с результатом да/нет. " +
        "Примеры: сдать экзамен, получить сертификат"),
    
    CUSTOM("Пользовательская цель", 
        "Цель с пользовательскими параметрами");
    
    private final String title;
    private final String description;
    
    GoalType(String title, String description) {
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