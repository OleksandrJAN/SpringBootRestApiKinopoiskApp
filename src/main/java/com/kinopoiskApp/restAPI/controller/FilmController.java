package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.FilmSortType;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.service.FilmService;
import com.kinopoiskApp.restAPI.service.GenreService;
import com.kinopoiskApp.restAPI.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    private final FilmService filmService;
    private final GenreService genreService;
    private final SortService sortService;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService, SortService sortService) {
        this.filmService = filmService;
        this.genreService = genreService;
        this.sortService = sortService;
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDto>> getFilms(
            @RequestParam(name = "sort") FilmSortType sortType,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Stream<Film> films = filmService.getFilms();
        // filtering by genre if needed
        Genre genre = genreService.getGenreByName(genreName);
        if (genre != null) {
            Predicate<Film> filmsFilterByGenre = sortService.getFilmsFilterByGenre(genre);
            films = films.filter(filmsFilterByGenre);
        }
        // filtering by country if needed
        if (!StringUtils.isEmpty(country)) {
            Predicate<Film> filmsFilterByCountry = sortService.getFilmsFilterByCountry(country);
            films = films.filter(filmsFilterByCountry);
        }
        // sorting by HumanSortType
        Comparator<Film> filmsComparator = sortService.getFilmsComparator(sortType);
        films = films.sorted(filmsComparator);
        // cast Film to FilmDto
        List<FilmDto> filmDtos = filmService.getFilmsDtos(films).collect(Collectors.toList());
        return new ResponseEntity<>(filmDtos, HttpStatus.OK);
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
