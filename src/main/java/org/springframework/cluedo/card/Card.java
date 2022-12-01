package org.springframework.cluedo.card;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.enumerates.CardName;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;

@Entity
@Table(name = "cards")
@Getter
public class Card extends BaseEntity {

    @Column(name="card_name")
    @NotNull
    private CardName cardName;

    @Column(name = "card_type")
    @NotNull
    private CardType cardType;

    @Column(name = "image_url")
    
    private String imageUrl;



}
