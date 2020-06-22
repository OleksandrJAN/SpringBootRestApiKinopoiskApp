package com.kinopoiskApp.restAPI.dto.factory;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmDtoFactory {

    public static FilmDto createFilmDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setFilmName(film.getFilmName());
        filmDto.setSlogan(film.getSlogan());
        filmDto.setCountry(film.getCountry());
        filmDto.setYear(film.getYear());
        filmDto.setAnnotation(film.getAnnotation());
        filmDto.setImage(film.getImage());

        List<String> genres = film.getFilmGenres().stream()
                .map(Genre::getName)
                .sorted()
                .collect(Collectors.toList());
        filmDto.setFilmGenres(genres);

        return filmDto;
    }
}
