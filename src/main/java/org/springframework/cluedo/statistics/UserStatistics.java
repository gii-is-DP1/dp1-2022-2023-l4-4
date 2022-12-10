package org.springframework.cluedo.statistics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_statistics")
public class UserStatistics extends BaseEntity {

    @Column(name="xp")
    @PositiveOrZero
    private Integer xp;

    @Column(name="total_games")
    @PositiveOrZero
    private Integer totalGames;

    @Column(name="total_time")
    @PositiveOrZero
    private Integer totalTime;

    @Column(name="total_rounds")
    @PositiveOrZero
    private Integer totalRounds;

    @Column(name="total_accusations")
    @PositiveOrZero
    private Integer totalAccusations;

    @Column(name="victories")
    @PositiveOrZero
    private Integer victories;

    @Column(name="afk_counter")
    @PositiveOrZero
    private Integer afkCounter;

    @OneToOne
    private Game longestGame;
    
    @OneToOne
    private Game shortestGame;

    @Column(name="total_final_accusations")
    @PositiveOrZero
    private Integer totalFinalAccusations;

    @OneToOne
    private User user;

    public UserStatistics(){
        this.id=null;
        this.xp=0;
        this.victories=0;
        this.totalTime=0;
        this.totalRounds=0;
        this.totalGames=0;
        this.totalFinalAccusations=0;
        this.totalAccusations=0;
        this.afkCounter=0;
        this.longestGame=null;
        this.shortestGame=null;
        this.user=null;
    }
    
}
