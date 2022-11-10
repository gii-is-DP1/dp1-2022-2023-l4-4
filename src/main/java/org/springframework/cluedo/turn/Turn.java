package org.springframework.cluedo.turn;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_game_id")
    private UserGame userGameId;

    @NotNull
    private Integer round;

    private Integer diceResult;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "initial_celd_id")
    private Celd initialCeld;

    @ManyToOne
    @JoinColumn(name = "final_celd_id")
    private Celd finalCeld;

    @NotNull
    private Phase phase;

}
