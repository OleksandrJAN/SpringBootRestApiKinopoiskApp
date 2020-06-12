package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDto>> getFilms() {
        List<Film> films = filmService.getFilms();
        List<FilmDto> filmsDtos = films.stream().map(
                FilmDto::new
        ).collect(Collectors.toList());
        return new ResponseEntity<>(filmsDtos, HttpStatus.OK);
    }

    @GetMapping("films/{film:[\\d]+}")
    public ResponseEntity<FilmDto> getFilm(@PathVariable Film film) {
        if (film != null) {
            FilmDto filmsDto = new FilmDto(film);
            return new ResponseEntity<>(filmsDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("films/{film:[\\d]+}/cast")
    public ResponseEntity<Map<String, List<HumanInfo>>> getFilmCast(@PathVariable Film film) {
        if (film != null) {
            Map<String, List<HumanInfo>> filmCast = filmService.getFilmCast(film);
            return new ResponseEntity<>(filmCast, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
