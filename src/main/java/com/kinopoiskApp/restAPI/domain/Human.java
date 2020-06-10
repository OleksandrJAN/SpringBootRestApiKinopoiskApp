package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "humans")
@Data
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


    @OneToMany(mappedBy = "human")
    @JsonBackReference
    Set<HumanRoleInFilm> humanRoles;



//    @ManyToMany(mappedBy = "filmMainRoles", fetch = FetchType.LAZY)
//    private List<Film> mainRolesFilms;
//
//    @ManyToMany(mappedBy = "filmCreators", fetch = FetchType.LAZY)
//    private List<Film> createdFilms;





    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "human_genres",
            joinColumns = @JoinColumn(name = "human_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> humanGenres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "human_careers",
            joinColumns = @JoinColumn(name = "human_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id"))
    private List<Career> humanCareers;

}
