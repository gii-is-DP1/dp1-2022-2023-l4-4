package org.springframework.samples.petclinic.acussation;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

public class Acussation extends BaseEntity{

    @Column(name = "acussation_id")
    @NotNull
    private Integer AcussationId;

    @Column(name = "turn_id")
    @NotNull
    private Integer TurnId;

    @Column(name = "suspect_card_id")
    @NotNull
    private Integer SuspectCardId;

    @Column(name = "weapon_card_id")
    @NotNull
    private Integer WeaponCardId;

    @Column(name = "room_card_id")
    @NotNull
    private Integer RoomCardId;
    
}
