package org.springframework.cluedo.game;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
	private User hostId;

    private Integer playersNumber;
    private boolean isPrivate;
    private SuspectType initialPlayer;

    @OneToOne
    @JoinColumn(name = "user_id")
	private User winner;

    private Duration duration;
    private Integer round;
    private SuspectType playerTurn;
    
}
