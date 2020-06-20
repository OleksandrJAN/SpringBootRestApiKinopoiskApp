package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }


    public Stream<Film> getFilms() {
        return filmRepo.findAll().stream();
    }

    public Stream<FilmDto> getFilmsDtos(Stream<Film> films) {
        return films.map(FilmDto::new);
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
        ).sorted(
                Entry.comparingByKey()
        ).collect(Collectors.groupingBy(Entry::getKey, Collectors.mapping(Entry::getValue, Collectors.toList())));
    }

}
