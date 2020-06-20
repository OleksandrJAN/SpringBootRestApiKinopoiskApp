package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SortService {

    public Predicate<Film> getFilmsFilterByCountry(String country) {
        return film -> film.getCountry().equals(country);
    }

    public Predicate<Film> getFilmsFilterByGenre(Genre genre) {
        return film -> film.getFilmGenres().contains(genre);
    }

    public Comparator<Film> getFilmsComparator(FilmSortType filmSortType) {
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


    public Predicate<Human> getHumansFilterByCareer(Career career) {
        return human -> {
            // set with one human's careers
            Set<Career> humanCareers = human.getHumanRoles().stream().map(
                    HumanRoleInFilm::getCareer
            ).collect(Collectors.toSet());
            return humanCareers.contains(career);
        };
    }

    public Predicate<Human> getHumansFilterByGenre(Genre genre) {
        return human -> {
            // set with films genres for one human
            Set<Genre> genres = human.getHumanRoles().stream().flatMap(
                    humanRoleInFilm -> {
                        Film film = humanRoleInFilm.getFilm();
                        return film.getFilmGenres().stream();
                    }
            ).collect(Collectors.toSet());
            return genres.contains(genre);
        };
    }

    public Comparator<Human> getHumansComparator(HumanSortType sortType) {
        switch (sortType) {
            case byName:
                return this.getHumanNameComparator();
            case byPopular:
            default:
                return this.getHumanPopularComparator().reversed();

        }
    }

    private Comparator<Human> getHumanNameComparator() {
        return (o1, o2) -> {
            String firstName1 = o1.getFirstName();
            String firstName2 = o2.getFirstName();

            int nameCompare = firstName1.compareTo(firstName2);
            if (nameCompare != 0) {
                return nameCompare;
            }

            String lastName1 = o1.getLastName();
            String lastName2 = o2.getLastName();
            return lastName1.compareTo(lastName2);
        };
    }

    private Comparator<Human> getHumanPopularComparator() {
        return (o1, o2) -> {
            Set<Film> humanFilms1 = this.getHumanFilms(o1);
            Set<Film> humanFilms2 = this.getHumanFilms(o2);
            Integer size1 = humanFilms1.size();
            Integer size2 = humanFilms2.size();
            return size1.compareTo(size2);
        };
    }

    private Set<Film> getHumanFilms(Human human) {
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        return humanFilms.stream().map(
                HumanRoleInFilm::getFilm
        ).collect(Collectors.toSet());
    }


}
