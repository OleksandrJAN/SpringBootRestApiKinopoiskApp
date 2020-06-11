package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import com.kinopoiskApp.restAPI.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public List<Genre> getGenres() {
        return genreRepo.findAll();
    }


    public List<Human> getHumanListSortedByCountGenreMatches(Genre genre) {
        //мапа с количеством совпадений в жанре для человека
        Map<Human, Integer> humanMapWithGenreMatches = new HashMap<>();

        //все фильмы жанра genre
        Set<Film> filmsWithGenre = genre.getFilms();
        for (Film film : filmsWithGenre) {
            //для каждого фильма из filmsWithGenre жанра genre
            //получаю всех людей и помещаю во множество humans
            Set<HumanRoleInFilm> humanRoles = film.getHumanRoles();
            Set<Human> humans = humanRoles.stream().map(
                    HumanRoleInFilm::getHuman
            ).collect(Collectors.toSet());

            //каждого человека закидываю в мапу
            for (Human human : humans) {
                Integer count = humanMapWithGenreMatches.get(human);
                humanMapWithGenreMatches.put(human, (count == null) ? 1 : count + 1);
            }
        }

        //сортирую мапу по убыванию количества совпадений с жанром и возвращаю остортированный список людей
        //обязательно список т.к. сортировка
        //проверить сортировку на правильность!!!!!!
        return humanMapWithGenreMatches.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
