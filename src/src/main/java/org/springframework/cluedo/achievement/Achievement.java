package org.springframework.cluedo.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement extends BaseEntity{

    @NotEmpty
    @Length(min=3, max = 50)
    @Column(name="achievement_name")
    private String name;

    @NotNull
    @Column(name="metric")
    private Metric metric;
    
    @NotNull
    @Column(name="badge")
    private Badge badgeType;

    @NotNull
    @Min(0)
    @Column(name="goal")
    private Integer goal;

    @NotEmpty
    @Length(min=3, max = 200)
    @Column(name="description")
    private String description;

    @NotNull
    @Min(0)
    @Column(name="experience")
    private Integer xp;

    @Column(name="image_url")
    private String imageUrl;

}
