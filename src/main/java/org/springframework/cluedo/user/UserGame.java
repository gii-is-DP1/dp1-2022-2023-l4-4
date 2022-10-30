package org.springframework.cluedo.user;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserGame extends BaseEntity{
    @NotNull
    private Integer gameId;
    @NotNull
    private Integer userId;
    @Min(0)
    @Value("${some.key:0}")
    private Integer accusationsNumber;
    @NotNull
    private Boolean isAfk;
    @NotNull
    private SuspectType suspect;

    @ManyToOne(optional = false)
    private User user;

}
