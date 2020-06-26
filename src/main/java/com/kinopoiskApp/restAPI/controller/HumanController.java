package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.dto.factory.HumanDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.FilmInfo;
import com.kinopoiskApp.restAPI.service.*;
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
public class HumanController {

    private final HumanService humanService;
    private final CareerService careerService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final FilmService filmService;

    @Autowired
    public HumanController(
            HumanService humanService,
            CareerService careerService,
            GenreService genreService,
            CountryService countryService,
            FilmService filmService
    ) {
        this.humanService = humanService;
        this.careerService = careerService;
        this.genreService = genreService;
        this.countryService = countryService;
        this.filmService = filmService;
    }


    @GetMapping("humans")
    public ResponseEntity<Page<HumanDto>> getHumans(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "byPopular") HumanSortType sortType,
            @RequestParam(name = "career", required = false) String careerName,
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        Career career = careerService.getCareerByName(careerName);
        Genre genre = genreService.getGenreByName(genreName);

        // filtering and sorting humans
        Stream<Human> humansStream = humanService.getHumans();
        humansStream = humanService.filteringHumans(humansStream, sortType, career, genre);
        List<Human> humansList = humansStream.collect(Collectors.toList());

        // create humans page
        Pageable pageable = PageRequest.of(page, 5);
        Page<Human> humansPage = humanService.getHumansPage(pageable, humansList);
        Page<HumanDto> humansDtosPage = humanService.getHumansDtosPage(humansPage);
        return new ResponseEntity<>(humansDtosPage, HttpStatus.OK);
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
            @RequestParam(name = "genre", required = false) String genreName
    ) {
        if (human != null) {
            Country country = countryService.getCountryByName(countryName);
            Genre genre = genreService.getGenreByName(genreName);

            // filtering and sorting humans
            Stream<Film> filmsStream = filmService.getFilms(human);
            filmsStream = filmService.filteringFilms(filmsStream, sortType, country, genre);

            // get map with <career name, list of films with career name>
            Map<String, List<FilmInfo>> humanRolesInFilms = humanService.getHumanCareersWithFilmsMap(human, filmsStream);
            return new ResponseEntity<>(humanRolesInFilms, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
