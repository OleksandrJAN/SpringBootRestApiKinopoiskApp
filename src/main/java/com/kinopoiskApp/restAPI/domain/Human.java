package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "humans")
@Getter
@Setter
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 127)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(max = 127)
    @Column(name = "last_name")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "total_films")
    @Positive
    private int totalFilms;

    @NotBlank
    @Length(max = 255)
    @Column(name = "image")
    private String image;


    @OneToMany(mappedBy = "human")
    @JsonBackReference
    private Set<HumanRoleInFilm> humanRoles;

}
