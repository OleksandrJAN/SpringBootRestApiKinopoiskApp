package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.FilmInfo;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.service.CareerService;
import com.kinopoiskApp.restAPI.service.GenreService;
import com.kinopoiskApp.restAPI.service.HumanService;
import com.kinopoiskApp.restAPI.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HumanController {

    private final HumanService humanService;
    private final GenreService genreService;
    private final CareerService careerService;
    private final SortService sortService;

    @Autowired
    public HumanController(
            HumanService humanService,
            GenreService genreService,
            CareerService careerService,
            SortService sortService
    ) {
        this.humanService = humanService;
        this.genreService = genreService;
        this.careerService = careerService;
        this.sortService = sortService;
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
            Predicate<Human> humansFilterByCareer = sortService.getHumansFilterByCareer(career);
            humans = humans.filter(humansFilterByCareer);
        }
        // filtering by genre if needed
        Genre genre = genreService.getGenreByName(genreName);
        if (genre != null) {
            Predicate<Human> humansFilterByGenre = sortService.getHumansFilterByGenre(genre);
            humans = humans.filter(humansFilterByGenre);
        }
        // sorting by HumanSortType
        Comparator<Human> humansComparator = sortService.getHumansComparator(sortType);
        humans = humans.sorted(humansComparator);
        // cast Human to HumanDto
        List<HumanDto> humanDtos = humanService.getHumansDtos(humans).collect(Collectors.toList());
        return new ResponseEntity<>(humanDtos, HttpStatus.OK);
    }

    @GetMapping("humans/careers")
    public ResponseEntity<List<String>> getHumansCareers() {
        Stream<Human> humans = humanService.getHumans();
        Set<Career> humansCareers = humanService.getHumansCareers(humans);
        List<String> careersNames = careerService.getSortedCareersNames(humansCareers);
        return new ResponseEntity<>(careersNames, HttpStatus.OK);
    }

    @GetMapping("humans/genres")
    public ResponseEntity<List<String>> getHumansGenres() {
        Stream<Human> humans = humanService.getHumans();
        Set<Genre> humansGenres = humanService.getHumansGenres(humans);
        List<String> genresNames = genreService.getSortedGenresNames(humansGenres);
        return new ResponseEntity<>(genresNames, HttpStatus.OK);
    }


    @GetMapping("humans/{human:[\\d]+}")
    public ResponseEntity<HumanDto> getHuman(@PathVariable Human human) {
        if (human != null) {
            HumanDto humanDto = new HumanDto(human);
            return new ResponseEntity<>(humanDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("humans/{human:[\\d]+}/roles")
    public ResponseEntity<Map<String, List<FilmInfo>>> getHumanRoles(
            @PathVariable Human human,
            @RequestParam(name = "sort") FilmSortType sortType,
            @RequestParam(name = "genre", required = false) String genre
    ) {
        if (human != null) {
            Set<Film> humanFilms = humanService.getHumanFilms(human);
            Stream<Film> filmStream = humanFilms.stream();

            Genre filteringGenre = genreService.getGenreByName(genre);
            if (filteringGenre != null) {
                Predicate<Film> filmsFilterByGenre = sortService.getFilmsFilterByGenre(filteringGenre);
                filmStream = filmStream.filter(filmsFilterByGenre);
            }

            Comparator<Film> filmsComparator = sortService.getFilmsComparator(sortType);
            filmStream = filmStream.sorted(filmsComparator);


            Map<String, List<FilmInfo>> humanRolesInFilms = humanService.getHumanCareersWithFilmsMap(human, filmStream);
            return new ResponseEntity<>(humanRolesInFilms, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("humans/{human:[\\d]+}/genres")
    public ResponseEntity<List<String>> getHumanGenres(@PathVariable Human human) {
        if (human != null) {
            Set<Genre> humanGenres = humanService.getHumanGenres(human);
            List<String> genresNames = genreService.getSortedGenresNames(humanGenres);
            return new ResponseEntity<>(genresNames, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
