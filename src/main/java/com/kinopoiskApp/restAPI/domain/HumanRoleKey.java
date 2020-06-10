package com.kinopoiskApp.restAPI.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class HumanRoleKey implements Serializable {

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "human_id")
    private Long humanId;

    @Column(name = "career_id")
    private Long careerId;

}
