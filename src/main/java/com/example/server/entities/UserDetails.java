package com.example.server.entities.visualConditions;
import jakarta.persistence.*;
import lombok.Data;
import com.example.server.enums.Rank;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nickname;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private String selfInformation;
    
    private String photoLink;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank rank = Rank.NOVICE;
    
    @Column(nullable = false)
    private int completedGoals = 0;
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Character character;

    public void incrementCompletedGoals() {
        this.completedGoals++;
        updateRank();
    }

    public void setCompletedGoals(int completedGoals) {
        this.completedGoals = completedGoals;
        updateRank();
    }

    private void updateRank() {
        this.rank = Rank.getRankByCompletedGoals(this.completedGoals);
    }

    public int getGoalsToNextRank() {
        return this.rank.getGoalsToNextRank(this.completedGoals);
    }

    public String getNextRankTitle() {
        return this.rank.getNextRank().getTitle();
    }

    public boolean isMaxRank() {
        return this.rank == Rank.GRANDMASTER;
    }
}
