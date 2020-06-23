package com.kinopoiskApp.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 127)
    @Column(name = "country_name")
    private String name;

    @ManyToMany(mappedBy = "filmCountries", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Film> films;
}
