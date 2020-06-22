package com.kinopoiskApp.restAPI.dto.links;

import com.kinopoiskApp.restAPI.domain.Film;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class FilmInfo {
    private Long filmId;
    private String filmName;

    public FilmInfo(Film film) {
        this.filmId = film.getId();
        this.filmName = film.getFilmName();
    }
}
