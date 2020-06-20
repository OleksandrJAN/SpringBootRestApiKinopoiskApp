package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.FilmInfo;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.repo.HumanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HumanService {

    private final HumanRepo humanRepo;

    @Autowired
    public HumanService(HumanRepo humanRepo) {
        this.humanRepo = humanRepo;
    }


    public Stream<Human> getHumans() {
        return humanRepo.findAll().stream();
    }

    public Stream<HumanDto> getHumansDtos(Stream<Human> humans) {
        return humans.map(HumanDto::new);
    }

    public Set<Film> getHumanFilms(Human human) {
        return human.getHumanRoles().stream().map(
                HumanRoleInFilm::getFilm
        ).collect(Collectors.toSet());
    }

    public Set<Career> getHumansCareers(Stream<Human> humans) {
        // return set with all humans careers
        return humans.flatMap(
                human -> {
                    Set<Career> humanCareer = this.getHumanCareers(human);
                    // return set with one human's careers
                    return humanCareer.stream();
                }
        ).collect(Collectors.toSet());
    }

    public Set<Career> getHumanCareers(Human human) {
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        // return set with one human's genres
        return humanFilms.stream().map(
                HumanRoleInFilm::getCareer
        ).collect(Collectors.toSet());
    }

    public Set<Genre> getHumansGenres(Stream<Human> humans) {
        // return set with genres of all humans
        return humans.flatMap(
                human -> {
                    Set<Genre> humanGenres = this.getHumanGenres(human);
                    return humanGenres.stream();
                }
        ).collect(Collectors.toSet());
    }

    public Set<Genre> getHumanGenres(Human human) {
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        // return set with one human's genres
        return humanFilms.stream().flatMap(
                humanRoleInFilm -> {
                    Film film = humanRoleInFilm.getFilm();
                    return film.getFilmGenres().stream();
                }
        ).collect(Collectors.toSet());
    }


    private Collection<Career> getCareersInFilm(Human human, Film film) {
        return film.getHumanRoles().stream()
                .filter(humanRoleInFilm -> humanRoleInFilm.getHuman().equals(human))
                .map(HumanRoleInFilm::getCareer)
                .collect(Collectors.toSet());
    }

    public Map<String, List<FilmInfo>> getHumanCareersWithFilmsMap(Human human, Stream<Film> humanFilms) {
        // grouping list of films by career name; return map with key career name and list of films
        return humanFilms
                .flatMap(
                        film -> {
                            Collection<Career> careersInFilm = this.getCareersInFilm(human, film);
                            // return list of pair with career name and film info
                            return careersInFilm.stream().map(
                                    career -> new SimpleEntry<>(career.getName(), new FilmInfo(film))
                            ).collect(Collectors.toList()).stream();
                        }
                ).collect(Collectors.groupingBy(Entry::getKey, Collectors.mapping(Entry::getValue, Collectors.toList())));
    }

}
