package org.springframework.cluedo.accusation;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.user.UserGame;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="accusations")
public class Accusation extends BaseAccusation{

    @OneToOne
    @JoinColumn(name = "turn")
    @NotNull
    private Turn turn;

    @ManyToOne
    @JoinColumn(name = "shown_card")
    private Card shownCard;

    @ManyToOne
    @JoinColumn(name = "player_who_shows")
    private UserGame playerWhoShows;
}
