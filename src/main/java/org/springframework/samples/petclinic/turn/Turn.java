package org.springframework.samples.petclinic.turn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Id;
import org.springframework.samples.petclinic.enumerates.Phase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true,nullable=false,precision=10)
    private long id;
    

    @ManyToOne
    @JoinColumn(name = "user_game_id")
    private UserGame userGameId;

    private Integer round;

    private Integer diceResult;
    
    @OneToMany
    @JoinColumn(name = "celd_id")
    private Celd initialPosition;

    @OneToMany
    @JoinColumn(name = "celd_id")
    private Celd finalPosition;

    private Phase phase;


}
