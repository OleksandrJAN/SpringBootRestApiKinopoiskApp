package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }


    public Stream<Genre> getGenres() {
        return genreRepo.findAll().stream();
    }

    public Genre getGenreByName(String genreName) {
        return genreRepo.findByName(genreName);
    }

    public List<String> getGenresNames(Stream<Genre> genres) {
        return genres
                .map(Genre::getName)
                .sorted()
                .collect(Collectors.toList());
    }

}
