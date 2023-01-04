package org.springframework.cluedo.accusation;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.game.Game;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CrimeScene extends BaseAccusation{

    @OneToOne
    @JoinColumn(name = "game_id")
    @NotNull
    private Game game;

}
