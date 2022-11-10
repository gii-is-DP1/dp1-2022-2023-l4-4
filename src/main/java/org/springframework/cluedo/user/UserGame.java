package org.springframework.cluedo.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserGame extends BaseEntity{

    private Integer order;

    @Min(0)
    @Value("${some.key:0}")
    private Integer accusationsNumber;
    
    @NotNull
    private Boolean isAfk;
    
    @NotNull
    private SuspectType suspect;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;
}
