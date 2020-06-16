package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.FilmInfo;
import com.kinopoiskApp.restAPI.repo.HumanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class HumanService {

    private final HumanRepo humanRepo;

    @Autowired
    public HumanService(HumanRepo humanRepo) {
        this.humanRepo = humanRepo;
    }

    public List<Human> getHumans() {
        return humanRepo.findAll();
    }


    private Map<String, List<Film>> getHumanRolesMap(Human human, Genre genre) {
        Map<String, List<Film>> humanRolesMap = new HashMap<>();
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        boolean isSortingByGenre = genre != null;

        humanFilms.forEach(
                humanRoleInFilm -> {
                    Film film = humanRoleInFilm.getFilm();

                    if (isSortingByGenre) {
                        if (!film.getFilmGenres().contains(genre)) {
                            return;
                        }
                    }

                    String careerName = humanRoleInFilm.getCareer().getName();
                    if (!humanRolesMap.containsKey(careerName)) {
                        humanRolesMap.put(careerName, new ArrayList<>());
                    }
                    humanRolesMap.get(careerName).add(film);
                }
        );
        return humanRolesMap;
    }

    public Map<String, List<FilmInfo>> getHumanRoles(Human human, HumanSortType humanSortType, Genre genre) {
        // map with Career name and List of Films of genre
        Map<String, List<Film>> humanRolesMap = getHumanRolesMap(human, genre);

        // sorting map values by humanSortType(film.year or film.name)
        Comparator<Film> comparator = Comparator.comparing(film -> {
            switch (humanSortType) {
                case byName:
                    return film.getFilmName();
                case byYear:
                default:
                    return String.valueOf(film.getYear());
            }
        });
        humanRolesMap.values().forEach(
                films -> films.sort(comparator)
        );

        // cast Film to FilmInfo
        return humanRolesMap.entrySet().stream().map(
                stringListEntry -> {
                    String careerName = stringListEntry.getKey();
                    List<Film> films = stringListEntry.getValue();
                    List<FilmInfo> filmsInfo = films.stream().map(
                            FilmInfo::new
                    ).collect(Collectors.toList());
                    return new SimpleEntry<>(careerName, filmsInfo);
                }
        ).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    public List<String> getHumanGenres(Human human) {
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        Set<Genre> allHumanGenres = humanFilms.stream().flatMap(
                humanRoleInFilm -> {
                    Film film = humanRoleInFilm.getFilm();
                    return film.getFilmGenres().stream();
                }
        ).collect(Collectors.toSet());
        return allHumanGenres.stream().map(
                Genre::getName
        ).sorted().collect(Collectors.toList());
    }

}
