package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.dto.factory.HumanDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.FilmInfo;
import com.kinopoiskApp.restAPI.service.*;
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
public class HumanController {

    private final HumanService humanService;
    private final GenreService genreService;
    private final CareerService careerService;
    private final CountryService countryService;

    @Autowired
    public HumanController(
            HumanService humanService,
            GenreService genreService,
            CareerService careerService,
            CountryService countryService
    ) {
        this.humanService = humanService;
        this.genreService = genreService;
        this.careerService = careerService;
        this.countryService = countryService;
    }


    @GetMapping("humans")
    public ResponseEntity<List<HumanDto>> getHumans(
            @RequestParam(name = "sort") HumanSortType sortType,
            @RequestParam(name = "career", required = false) String careerName,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Stream<Human> humans = humanService.getHumans();
        // filtering by career if needed
        Career career = careerService.getCareerByName(careerName);
        if (career != null) {
            Predicate<Human> humansFilterByCareer = SortService.getHumansFilterByCareer(career);
            humans = humans.filter(humansFilterByCareer);
        }
        // filtering by genre if needed
        Genre genre = genreService.getGenreByName(genreName);
        if (genre != null) {
            Predicate<Human> humansFilterByGenre = SortService.getHumansFilterByGenre(genre);
            humans = humans.filter(humansFilterByGenre);
        }
        // sorting by HumanSortType
        Comparator<Human> humansComparator = SortService.getHumansComparator(sortType);
        humans = humans.sorted(humansComparator);
        // cast Human to HumanDto
        List<HumanDto> humanDtos = humanService.getHumansDtos(humans).collect(Collectors.toList());
        return new ResponseEntity<>(humanDtos, HttpStatus.OK);
    }


    @GetMapping("humans/{human:[\\d]+}")
    public ResponseEntity<HumanDto> getHuman(@PathVariable Human human) {
        if (human != null) {
            HumanDto humanDto = HumanDtoFactory.createHumanDto(human);
            return new ResponseEntity<>(humanDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("humans/{human:[\\d]+}/films")
    public ResponseEntity<Map<String, List<FilmInfo>>> getHumanFilms(
            @PathVariable Human human,
            @RequestParam(name = "sort") FilmSortType sortType,
            @RequestParam(name = "country", required = false) String countryName,
            @RequestParam(name = "genre", required = false) String genre
    ) {
        if (human != null) {
            Stream<Film> films = humanService.getHumanFilms(human);
            // filtering by country if needed
            Country country = countryService.getCountryByName(countryName);
            if (country != null) {
                Predicate<Film> filmsFilterByCountry = SortService.getFilmsFilterByCountry(country);
                films = films.filter(filmsFilterByCountry);
            }
            // filtering by genre if needed
            Genre filteringGenre = genreService.getGenreByName(genre);
            if (filteringGenre != null) {
                Predicate<Film> filmsFilterByGenre = SortService.getFilmsFilterByGenre(filteringGenre);
                films = films.filter(filmsFilterByGenre);
            }
            // sorting by sortType
            Comparator<Film> filmsComparator = SortService.getFilmsComparator(sortType);
            films = films.sorted(filmsComparator);
            // get map with <career name, list of films with career name>
            Map<String, List<FilmInfo>> humanRolesInFilms = humanService.getHumanCareersWithFilmsMap(human, films);
            return new ResponseEntity<>(humanRolesInFilms, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
