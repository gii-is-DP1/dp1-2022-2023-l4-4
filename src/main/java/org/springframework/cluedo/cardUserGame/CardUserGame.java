package org.springframework.cluedo.cardUserGame;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.model.BaseEntity;


@Entity
@Table(name = "cardUserGames")
public class CardUserGame extends BaseEntity{

    @Column(name = "card_id")
    @NotNull
    private Integer CardId;
    
    @Column(name = "user_game_id")
    @NotNull
    private Integer UserGameId;


}
