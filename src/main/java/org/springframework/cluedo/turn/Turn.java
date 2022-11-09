package org.springframework.cluedo.turn;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.UserGame;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Turn extends BaseEntity{
    

    @ManyToOne
    @JoinColumn(name = "user_game_id")
    private UserGame userGameId;

    private Integer round;

    private Integer diceResult;
    
    @OneToMany
    @JoinColumn(name = "celd_id")
    private Celd initialPosition;

    @OneToMany
    @JoinColumn(name = "celd_id")
    private Celd finalPosition;

    private Phase phase;

}
