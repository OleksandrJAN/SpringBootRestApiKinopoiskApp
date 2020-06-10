package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "genres")
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 127)
    @Column(name = "genre_name")
    private String name;


    @ManyToMany(mappedBy = "filmGenres", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Film> films;

    @ManyToMany(mappedBy = "humanGenres", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Human> humans;
}
