package org.springframework.cluedo.celd;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.cluedo.model.BaseEntity;
import org.springframework.cluedo.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "celd")
public class Celd extends BaseEntity{

    @Column(name="celd_type")
    private CeldType celdType;

    @Column(name="connected_celds")
    private List<Celd> connectectedCelds;
    
}
