package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.dto.GenreDto;
import com.kinopoiskApp.restAPI.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("genres/{genre:[\\d]+}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable Genre genre) {
        if (genre != null) {
            List<Human> sortedHumansWithGenre = genreService.getHumanListSortedByCountGenreMatches(genre);

            GenreDto genreDto = new GenreDto(genre, sortedHumansWithGenre);
            return new ResponseEntity<>(genreDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
