package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Film;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class FilmDto {

    private Long id;
    private String filmName;
    private String slogan;
    private String country;
    private int year;
    private String annotation;
    private String image;

    private Set<GenreInfo> filmGenres;


    public FilmDto(Film film) {
        this.id = film.getId();
        this.filmName = film.getFilmName();
        this.slogan = film.getSlogan();
        this.country = film.getCountry();
        this.year = film.getYear();
        this.annotation = film.getAnnotation();
        this.image = film.getImage();

        this.filmGenres = film.getFilmGenres().stream().map(
                GenreInfo::new
        ).collect(Collectors.toSet());
    }
}
