package org.springframework.cluedo.game;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.accusation.CrimeScene;
import org.springframework.cluedo.enumerates.Status;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "host_id")
	private User host;

    @Min(3)
    @Max(6)
    @NotNull
    private Integer lobbySize; 

    @NotNull
    private Boolean isPrivate;

    @NotNull
    private Status status;

    @ManyToOne
    @JoinColumn(name = "winner_id")
	private User winner;

    private Timestamp startTime;

    private Timestamp endTime;

    @Min(0)
    private Integer round;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="lobbies",joinColumns = @JoinColumn(
        name = "game_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(
        name = "user_id", referencedColumnName = "id"),uniqueConstraints = @UniqueConstraint(columnNames={"game_id", "user_id"} ))
    private List<User> lobby;

    @ManyToMany
    @JoinTable(name="players",joinColumns = @JoinColumn(
        name = "game_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(
        name = "user_game_id", referencedColumnName = "id"))
    private List<UserGame> players;
    
    @ManyToOne
    @JoinColumn(name="crime_scene")
    private CrimeScene crimeScene;

    @ManyToOne
    @JoinColumn(name="actual_Player")
    private UserGame actualPlayer;
    


    public Boolean getIsPrivate(){
        return isPrivate;
    }

    public void addPlayers(List<UserGame> newPlayers){
        this.players.addAll(newPlayers);
    }

    public void addPlayers(UserGame newPlayer){
        this.players.add(newPlayer);
    }

    public void addLobbyUsers(List<User> lobbyUsers){
        this.lobby.addAll(lobbyUsers);
    }

    public void addLobbyUser(User lobbyUser){
        this.lobby.add(lobbyUser);
    }

    public void removeLobbyUsers(List<User> lobbyUsers){
        this.lobby.removeAll(lobbyUsers);
    }

    public void removeLobbyUser(User lobbyUser){
        this.lobby.remove(lobbyUser);
    }

    public UserGame lastPlayer(){
        return players.get(players.size()-1);
    }

    public Duration getDuration() {
        if(startTime == null || endTime == null) {
            return null;
        }
        return Duration.between(startTime.toInstant(), endTime.toInstant());
    }
}
