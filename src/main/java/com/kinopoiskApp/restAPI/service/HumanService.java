package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.dto.factory.HumanDtoFactory;
import com.kinopoiskApp.restAPI.dto.links.FilmInfo;
import com.kinopoiskApp.restAPI.repo.HumanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
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

    public Stream<Human> filteringHumans(Stream<Human> humans, HumanSortType sortType, Career career, Genre genre) {
        // filtering humans
        if (career != null) {
            Predicate<Human> humansFilterByCareer = SortService.getHumansFilterByCareer(career);
            humans = humans.filter(humansFilterByCareer);
        }
        if (genre != null) {
            Predicate<Human> humansFilterByGenre = SortService.getHumansFilterByGenre(genre);
            humans = humans.filter(humansFilterByGenre);
        }

        // sorting by HumanSortType
        Comparator<Human> humansComparator = SortService.getHumansComparator(sortType);
        humans = humans.sorted(humansComparator);
        return humans;
    }


    public Page<Human> getHumansPage(Pageable pageable, List<Human> humans) {
        //get sub list
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), humans.size());
        List<Human> humansSubList = humans.subList(start, end);
        return new PageImpl<>(humansSubList, pageable, humans.size());
    }

    public Page<HumanDto> getHumansDtosPage(Page<Human> humans) {
        return humans.map(HumanDtoFactory::createHumanDto);
    }


    public Map<String, List<FilmInfo>> getHumanCareersWithFilmsMap(Human human, Stream<Film> humanFilms) {
        // grouping list of films by career name; return map with key career name and list of films
        return humanFilms
                .flatMap(film -> {
                    Stream<Career> careersInFilm = careerService.getHumanCareersInFilm(human, film);
                    // return list of pair with career name and film info
                    return careersInFilm
                            .map(career -> new SimpleEntry<>(career.getName(), new FilmInfo(film)))
                            .collect(Collectors.toList())
                            .stream();
                })
                .collect(Collectors.groupingBy(
                        Entry::getKey,
                        Collectors.mapping(Entry::getValue, Collectors.toList())
                ));
    }

}
