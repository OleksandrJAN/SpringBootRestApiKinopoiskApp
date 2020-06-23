package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Country;
import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.FilmSortType;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.factory.FilmDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.HumanInfo;
import com.kinopoiskApp.restAPI.service.CountryService;
import com.kinopoiskApp.restAPI.service.FilmService;
import com.kinopoiskApp.restAPI.service.GenreService;
import com.kinopoiskApp.restAPI.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CountryService countryService;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService, CountryService countryService) {
        this.filmService = filmService;
        this.genreService = genreService;
        this.countryService = countryService;
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDto>> getFilms(
            @RequestParam(name = "sort") FilmSortType sortType,
            @RequestParam(name = "country", required = false) String countryName,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Stream<Film> films = filmService.getFilms();
        // filtering by country if needed
        Country country = countryService.getCountryByName(countryName);
        if (country != null) {
            Predicate<Film> filmsFilterByCountry = SortService.getFilmsFilterByCountry(country);
            films = films.filter(filmsFilterByCountry);
        }
        // filtering by genre if needed
        Genre genre = genreService.getGenreByName(genreName);
        if (genre != null) {
            Predicate<Film> filmsFilterByGenre = SortService.getFilmsFilterByGenre(genre);
            films = films.filter(filmsFilterByGenre);
        }
        // sorting by HumanSortType
        Comparator<Film> filmsComparator = SortService.getFilmsComparator(sortType);
        films = films.sorted(filmsComparator);
        // cast Film to FilmDto
        List<FilmDto> filmDtos = filmService.getFilmsDtos(films).collect(Collectors.toList());
        return new ResponseEntity<>(filmDtos, HttpStatus.OK);
    }

    @GetMapping("films/{film:[\\d]+}")
    public ResponseEntity<FilmDto> getFilm(@PathVariable Film film) {
        if (film != null) {
            FilmDto filmDto = FilmDtoFactory.createFilmDto(film);
            return new ResponseEntity<>(filmDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("films/{film:[\\d]+}/cast")
    public ResponseEntity<Map<String, List<HumanInfo>>> getFilmCast(@PathVariable Film film) {
        if (film != null) {
            // get map with career name and list of humans in film
            Map<String, List<HumanInfo>> filmCast = filmService.getFilmCast(film);
            return new ResponseEntity<>(filmCast, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
