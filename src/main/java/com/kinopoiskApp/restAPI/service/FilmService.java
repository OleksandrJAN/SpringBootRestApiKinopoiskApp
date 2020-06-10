package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public List<Film> getFilms() {
        return filmRepo.findAll();
    }

}
