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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final CountryService countryService;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService, CountryService countryService) {
        this.filmService = filmService;
        this.genreService = genreService;
        this.countryService = countryService;
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
        Sort filmsSort = SortService.getFilmsSort(sortType);
        Pageable pageable = PageRequest.of(page, 5, filmsSort);

        Page<Film> films = filmService.getFilms(country, genre, pageable);
        Page<FilmDto> filmsDtos = filmService.getFilmsDtos(films);
        return new ResponseEntity<>(filmsDtos, HttpStatus.OK);
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
