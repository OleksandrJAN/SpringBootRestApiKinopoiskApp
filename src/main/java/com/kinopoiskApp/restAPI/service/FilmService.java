package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public List<FilmDto> getFilms(FilmSortType sortType, String country, Genre genre) {
        List<Film> films = filmRepo.findAll();
        Predicate<Film> filterByCountry = getFilmFilterByCountry(country);
        Predicate<Film> filterByGenre = getFilmFilterByGenre(genre);
        Comparator<Film> filmComparator = getFilmComparator(sortType);
        return films.stream()
                .filter(filterByCountry)
                .filter(filterByGenre)
                .sorted(filmComparator)
                .map(FilmDto::new)
                .collect(Collectors.toList());
    }

    public List<String> getFilmsCountries() {
        List<Film> films = filmRepo.findAll();
        return films.stream().map(
                Film::getCountry
        ).distinct().sorted().collect(Collectors.toList());
    }

    public List<String> getFilmsGenres() {
        List<Film> films = filmRepo.findAll();
        return films.stream().flatMap(
                film -> {
                    Set<Genre> filmGenres = film.getFilmGenres();
                    return filmGenres.stream().map(
                            Genre::getName
                    ).collect(Collectors.toSet()).stream();
                }
        ).distinct().sorted().collect(Collectors.toList());
    }

    public Map<String, List<HumanInfo>> getFilmCast(Film film) {
        Set<HumanRoleInFilm> humanRoles = film.getHumanRoles();
        // return map with career name and list of humans
        return humanRoles.stream().map(
                humanRoleInFilm -> {
                    Human human = humanRoleInFilm.getHuman();
                    String careerName = humanRoleInFilm.getCareer().getName();
                    // return pair with film's career name and human
                    return new SimpleEntry<>(careerName, new HumanInfo(human));
                }
        ).collect(Collectors.groupingBy(Entry::getKey, Collectors.mapping(Entry::getValue, Collectors.toList())));
    }

    private Predicate<Film> getFilmFilterByCountry(String country) {
        return film -> StringUtils.isEmpty(country) || film.getCountry().equals(country);
    }

    private Predicate<Film> getFilmFilterByGenre(Genre genre) {
        return film -> genre == null || film.getFilmGenres().contains(genre);
    }

    public Comparator<Film> getFilmComparator(FilmSortType filmSortType) {
        return Comparator.comparing(film -> {
            switch (filmSortType) {
                case byName:
                    return film.getFilmName();
                case byYear:
                default:
                    return String.valueOf(film.getYear());
            }
        });
    }

}
