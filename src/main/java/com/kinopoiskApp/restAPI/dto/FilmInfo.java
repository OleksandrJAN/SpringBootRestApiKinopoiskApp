package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Film;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmInfo {
    private Long filmId;
    private String filmName;

    FilmInfo(Film film) {
        this.filmId = film.getId();
        this.filmName = film.getFilmName();
    }
}
