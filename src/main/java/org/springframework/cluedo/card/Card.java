package org.springframework.cluedo.card;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.model.NamedEntity;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name="card_name")
    @NotNull
    private CardName cardName;

    @Column(name = "card_type")
    @NotNull
    private CardType cardType;

    @Column(name = "image_url")
    @NotNull
    private String imageUrl;



}
