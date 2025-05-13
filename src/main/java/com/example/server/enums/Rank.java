package enums;

public enum Rank {
    // Начинающий
    NOVICE(0, "Новичок", "Только начал свой путь саморазвития"),
    
    // Начинающий практик
    APPRENTICE(5, "Ученик", "Выполнил первые цели и набирает опыт"),
    
    // Опытный практик
    PRACTITIONER(15, "Практик", "Регулярно достигает поставленных целей"),
    
    // Умелый практик
    SKILLFUL(30, "Умелец", "Демонстрирует стабильный прогресс и дисциплину"),
    
    // Опытный мастер
    EXPERT(50, "Эксперт", "Достиг значительных результатов в саморазвитии"),
    
    // Мастер
    MASTER(100, "Мастер", "Показывает выдающиеся результаты и помогает другим"),
    
    // Великий мастер
    GRANDMASTER(200, "Великий Мастер", "Достиг вершин в саморазвитии и вдохновляет других");

    private final int requiredCompletedGoals;
    private final String title;
    private final String description;

    Rank(int requiredCompletedGoals, String title, String description) {
        this.requiredCompletedGoals = requiredCompletedGoals;
        this.title = title;
        this.description = description;
    }

    public int getRequiredCompletedGoals() {
        return requiredCompletedGoals;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static Rank getRankByCompletedGoals(int completedGoals) {
        Rank[] ranks = Rank.values();
        Rank currentRank = NOVICE;
        
        for (Rank rank : ranks) {
            if (completedGoals >= rank.requiredCompletedGoals) {
                currentRank = rank;
            } else {
                break;
            }
        }
        
        return currentRank;
    }

    public Rank getNextRank() {
        Rank[] ranks = Rank.values();
        int currentIndex = this.ordinal();
        if (currentIndex < ranks.length - 1) {
            return ranks[currentIndex + 1];
        }
        return this;
    }

    public int getGoalsToNextRank(int completedGoals) {
        Rank nextRank = getNextRank();
        if (nextRank == this) {
            return 0; // Уже максимальный ранг
        }
        return nextRank.requiredCompletedGoals - completedGoals;
    }
}
