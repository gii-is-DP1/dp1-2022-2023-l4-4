package org.springframework.cluedo.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notifications")

public class Notification extends BaseEntity{

    @Column(name = "text")
    @NotEmpty
    private String text;

    @Column(name = "link")
    private Integer link;

    @Column(name= "timestamp")
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private User receiver;

}
