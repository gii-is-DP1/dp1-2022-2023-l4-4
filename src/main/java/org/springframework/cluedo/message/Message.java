package org.springframework.cluedo.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "message")
public class Message extends BaseEntity{
    
    @NotBlank
    @Size(min=1, max = 150)
    @JoinColumn(name= "text")
    private String text;

    @ManyToOne
    private User player;

    @ManyToOne
    @JoinColumn(name= "game_id")
    private Game game;

}
