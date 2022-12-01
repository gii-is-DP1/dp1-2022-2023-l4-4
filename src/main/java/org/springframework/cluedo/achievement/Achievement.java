package org.springframework.cluedo.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.cluedo.enumerates.Metric;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Achievement extends BaseEntity{

    @Column(name="achievement_name")
    private String name;

    @Column(name="metric")
    private Metric metric;

    @Column(name="goal")
    private Integer goal;

    @Column(name="description")
    private String description;

    @Column(name="experience")
    private Integer xp;

    @Column(name="image_url")
    private String imageUrl;
}
