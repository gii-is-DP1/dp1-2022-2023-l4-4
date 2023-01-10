package org.springframework.cluedo.user;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_games")
public class UserGame extends BaseEntity{
    @Column
    @Min(1)
    @Max(6)    
    private Integer orderUser;

    @Min(0)
    @Value("${some.key:0}")
    private Integer accusationsNumber;
    
    @NotNull
    private Boolean isAfk;
    
    @NotNull
    private Boolean isEliminated;
    
    @NotNull
    private SuspectType suspect;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToMany
    @JoinTable(name="user_cards")
    private Set<Card> cards;

    public void addCard(Card card){
        cards.add(card);
    }

    private Integer position;

    public Integer getX() {
        return (int)((position%24) * 20) - 15;
    } 

    public Integer getY() {
        return (Math.floorDiv(position, 24) * 20) + 10;
    }

}
