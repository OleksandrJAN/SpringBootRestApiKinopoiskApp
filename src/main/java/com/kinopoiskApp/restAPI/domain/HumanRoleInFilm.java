package com.kinopoiskApp.restAPI.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "film_human_roles")
@Getter
@Setter
public class HumanRoleInFilm {

    @EmbeddedId
    private HumanRoleKey id;

    @ManyToOne
    @MapsId("film_id")
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @MapsId("human_id")
    @JoinColumn(name = "human_id")
    private Human human;

    @ManyToOne
    @MapsId("career_id")
    @JoinColumn(name = "career_id")
    private Career career;

}
