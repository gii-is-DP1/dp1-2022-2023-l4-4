package org.springframework.cluedo.accusation;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.turn.Turn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Accusation extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "turn_id")
    @NotNull
    private Turn turn;

    @ManyToOne
    @JoinColumn(name = "suspect_card_id")
    @NotNull
    private Card suspectCard;

    @ManyToOne
    @JoinColumn(name = "weapon_card_id")
    @NotNull
    private Card weaponCard;

    @ManyToOne
    @JoinColumn(name = "room_card_id")
    @NotNull
    private Card roomCard;
    
    @ManyToOne
    @JoinColumn(name = "shown_card_id")
    private Card shownCard;
}
