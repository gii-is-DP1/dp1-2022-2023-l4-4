package org.springframework.samples.petclinic.celd;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "celd_celd")
public class CeldCeld extends BaseEntity{

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="celd_id")
    @NotNull
    @Column(name = "celd1")
    private Celd celd1;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="celd_id")
    @NotNull
    @Column(name="celd2")
    private Celd celd2;
    
}
