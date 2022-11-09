package org.springframework.cluedo.cardUserGame;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.UserGame;

@Entity
@Table(name = "userGameCards")
public class CardUserGame extends BaseEntity{
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card Card;
    
    @ManyToOne
    @JoinColumn(name = "user_game_id")
    private UserGame UserGame;


}
