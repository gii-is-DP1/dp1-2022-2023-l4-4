package org.springframework.cluedo.game;

import java.time.Duration;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.enumerates.Status;

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

    @Min(3)
    @Max(6)
    @NotNull
    private Integer lobbySize; 

    @NotNull
    private boolean isPrivate;

    @NotNull
    private Status status;

    @OneToOne
    @JoinColumn(name = "winner_id")
	private User winner;

    private Duration duration;

    private Integer round;

    @ManyToMany
    @JoinTable(name="lobby")
    private List<User> lobby;

    @ManyToMany
    @JoinTable(name="players")
    private List<UserGame> players;
    
    @ManyToOne
    @JoinColumn(name="crime_scene")
    private Accusation crimeScene;

    @ManyToOne
    @JoinColumn(name="actual_Player")
    private UserGame actualPlayer;

}
