package org.springframework.cluedo.celd;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "celd")
public class Celd extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="celd_type")
    private CeldType celdType;

    @NotNull
    @Column(name = "position")
    private Integer position;

    @ManyToMany
    @JoinTable(name="connected_celds",joinColumns = @JoinColumn(
       name = "id1", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
       name = "id2", referencedColumnName = "id"))
    private List<Celd> connectedCelds;
    
    @Override
    public  boolean equals(Object o){
      Celd celd2 = (Celd) o;
      return this.id.equals(celd2.id);
    }
}
