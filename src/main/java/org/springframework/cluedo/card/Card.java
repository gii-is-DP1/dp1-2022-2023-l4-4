package org.springframework.cluedo.card;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.enumerates.CardName;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card extends BaseEntity {

    @Column(name="card_name")
    @NotNull
    @Enumerated(EnumType.STRING)
    private CardName cardName;

    @Column(name = "card_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "image_url")
    
    private String imageUrl;



}
