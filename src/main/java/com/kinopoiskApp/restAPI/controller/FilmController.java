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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    private final FilmService filmService;
    private final CountryService countryService;
    private final GenreService genreService;

    @Autowired
    public FilmController(FilmService filmService, CountryService countryService, GenreService genreService) {
        this.filmService = filmService;
        this.countryService = countryService;
        this.genreService = genreService;
    }

    @GetMapping("films")
    public ResponseEntity<Page<FilmDto>> getFilms(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "byYear") FilmSortType sortType,
            @RequestParam(name = "country", required = false) String countryName,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Country country = countryService.getCountryByName(countryName);
        Genre genre = genreService.getGenreByName(genreName);

        // filtering and sorting humans
        Stream<Film> filmsStream = filmService.getFilms();
        filmsStream = filmService.filteringFilms(filmsStream, sortType, country, genre);
        List<Film> filmsList = filmsStream.collect(Collectors.toList());

        // create humans page
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> filmsPage = filmService.getFilmsPage(pageable, filmsList);
        Page<FilmDto> filmsDtosPage = filmService.getFilmsDtosPage(filmsPage);
        return new ResponseEntity<>(filmsDtosPage, HttpStatus.OK);
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
