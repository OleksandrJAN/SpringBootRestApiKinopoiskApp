package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping("genres")
    public ResponseEntity<List<String>> getGenres() {
        List<String> genresNames = genreService.getGenresNames();
        return new ResponseEntity<>(genresNames, HttpStatus.OK);
    }

}
