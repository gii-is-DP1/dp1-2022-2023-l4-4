package org.springframework.cluedo.chat;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import javax.persistence.Table;

import org.springframework.cluedo.message.Message;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

@Table(name = "chat")
public class Chat extends BaseEntity{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="message_id")
    private List<Message> messages;
    
    @OneToOne
    @JoinColumn(name="game_id")
    private Game game;
}