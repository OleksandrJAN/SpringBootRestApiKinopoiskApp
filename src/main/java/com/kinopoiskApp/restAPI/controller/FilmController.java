package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
}
