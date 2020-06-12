package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 127)
    @Column(name = "name")
    private String filmName;

    @NotBlank
    @Length(max = 127)
    @Column(name = "slogan")
    private String slogan;

    @NotBlank
    @Length(max = 127)
    @Column(name = "country")
    private String country;

    @Column(name = "year")
    @Positive
    private int year;

    @NotBlank
    @Length(max = 2047)
    @Column(name = "annotation")
    private String annotation;

    @NotBlank
    @Length(max = 255)
    @Column(name = "image")
    private String image;


    @OneToMany(mappedBy = "film")
    @JsonBackReference
    private Set<HumanRoleInFilm> humanRoles;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> filmGenres;

}
