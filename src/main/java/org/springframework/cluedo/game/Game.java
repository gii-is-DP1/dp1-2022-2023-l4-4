package org.springframework.cluedo.game;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "host_id")
	private User host;

    private Integer playersNumber;
    private boolean isPrivate;
    private Status status;
    private SuspectType initialPlayer;

    @OneToOne
    @JoinColumn(name = "winner_id")
	private User winner;

    private Duration duration;
    private Integer round;
    private SuspectType playerTurn;
    
}
