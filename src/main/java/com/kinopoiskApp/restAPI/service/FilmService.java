package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.FilmDto;
import com.kinopoiskApp.restAPI.dto.factory.FilmDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.HumanInfo;
import com.kinopoiskApp.restAPI.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService {

    private final FilmRepo filmRepo;
    private final CareerService careerService;

    @Autowired
    public FilmService(FilmRepo filmRepo, CareerService careerService) {
        this.filmRepo = filmRepo;
        this.careerService = careerService;
    }


    public Stream<Film> getFilms() {
        return filmRepo.findAll().stream();
    }

    public Stream<FilmDto> getFilmsDtos(Stream<Film> films) {
        return films.map(FilmDtoFactory::createFilmDto);
    }

    private Stream<Human> getFilmHumans(Film film) {
        return film.getHumanRoles().stream()
                .map(HumanRoleInFilm::getHuman)
                .distinct();
    }

    public Map<String, List<HumanInfo>> getFilmCast(Film film) {
        // get stream of humans in film
        Stream<Human> humans = this.getFilmHumans(film);
        // sorting humans by popular and then by name
        Comparator<Human> humansPopularComparator = SortService.getHumansComparator(HumanSortType.byPopular);
        Comparator<Human> humansNameComparator = SortService.getHumansComparator(HumanSortType.byName);
        humans = humans.sorted(humansPopularComparator.thenComparing(humansNameComparator));
        // return map with career name and human info with this career in film
        return humans
                .flatMap(human -> {
                    Stream<Career> careersInFilm = careerService.getHumanCareersInFilm(human, film);
                    // return list of pair with career name and human info
                    return careersInFilm
                            .map(career -> new SimpleEntry<>(career.getName(), new HumanInfo(human)))
                            .collect(Collectors.toList()).stream();
                })
                .collect(Collectors.groupingBy(
                        Entry::getKey,
                        Collectors.mapping(Entry::getValue, Collectors.toList())
                ));
    }

}
