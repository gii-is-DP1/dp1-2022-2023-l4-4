package org.springframework.cluedo.accusation;


import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseAccusation extends BaseEntity{
    
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
    
    public List<Card> getCards(){
        return List.of(suspectCard,weaponCard,roomCard);
    }
}
