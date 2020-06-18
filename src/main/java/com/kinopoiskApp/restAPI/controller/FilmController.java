package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.FilmSortType;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.service.FilmService;
import com.kinopoiskApp.restAPI.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    private final FilmService filmService;
    private final GenreService genreService;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDto>> getFilms(
            @RequestParam FilmSortType sort,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Genre genre = genreService.getGenreByName(genreName);
        List<FilmDto> films = filmService.getFilms(sort, country, genre);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("films/countries")
    public ResponseEntity<List<String>> getFilmsCountries() {
        List<String> filmsCountries = filmService.getFilmsCountries();
        return new ResponseEntity<>(filmsCountries, HttpStatus.OK);
    }

    @GetMapping("films/genres")
    public ResponseEntity<List<String>> getFilmsGenres() {
        List<String> filmsGenres = filmService.getFilmsGenres();
        return new ResponseEntity<>(filmsGenres, HttpStatus.OK);
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
