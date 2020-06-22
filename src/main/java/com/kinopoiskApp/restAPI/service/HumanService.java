package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.dto.factory.HumanDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.FilmInfo;
import com.kinopoiskApp.restAPI.repo.HumanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HumanService {

    private final HumanRepo humanRepo;
    private final CareerService careerService;

    @Autowired
    public HumanService(HumanRepo humanRepo, CareerService careerService) {
        this.humanRepo = humanRepo;
        this.careerService = careerService;
    }


    public Stream<Human> getHumans() {
        return humanRepo.findAll().stream();
    }

    public Stream<HumanDto> getHumansDtos(Stream<Human> humans) {
        return humans.map(HumanDtoFactory::createHumanDto);
    }


    public Stream<Film> getHumanFilms(Human human) {
        return human.getHumanRoles().stream()
                .map(HumanRoleInFilm::getFilm)
                .distinct();
    }

    public Stream<Genre> getHumanGenres(Human human) {
        return human.getHumanRoles().stream()
                .flatMap(humanRoleInFilm -> {
                    Film film = humanRoleInFilm.getFilm();
                    return film.getFilmGenres().stream();
                })
                .distinct();
    }


    public Map<String, List<FilmInfo>> getHumanCareersWithFilmsMap(Human human, Stream<Film> humanFilms) {
        // grouping list of films by career name; return map with key career name and list of films
        return humanFilms
                .flatMap(film -> {
                    Stream<Career> careersInFilm = careerService.getHumanCareersInFilm(human, film);
                    // return list of pair with career name and film info
                    return careersInFilm
                            .map(career -> new SimpleEntry<>(career.getName(), new FilmInfo(film)))
                            .collect(Collectors.toList()).stream();
                })
                .collect(Collectors.groupingBy(
                        Entry::getKey,
                        Collectors.mapping(Entry::getValue, Collectors.toList())
                ));
    }

}
