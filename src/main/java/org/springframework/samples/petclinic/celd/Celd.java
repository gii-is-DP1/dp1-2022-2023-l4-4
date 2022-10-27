package org.springframework.samples.petclinic.celd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "celd")
public class Celd extends BaseEntity{

    @Column(name="celdType")
    private CeldType celdType;
    
}
