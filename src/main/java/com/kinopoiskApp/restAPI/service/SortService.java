package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SortService {

    public static Predicate<Film> getFilmsFilterByCountry(String country) {
        return film -> film.getCountry().equals(country);
    }

    public static Predicate<Film> getFilmsFilterByGenre(Genre genre) {
        return film -> film.getFilmGenres().contains(genre);
    }

    public static Comparator<Film> getFilmsComparator(FilmSortType filmSortType) {
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


    public static Predicate<Human> getHumansFilterByCareer(Career career) {
        return human -> {
            // set with one human's careers
            Set<Career> humanCareers = human.getHumanRoles().stream().map(
                    HumanRoleInFilm::getCareer
            ).collect(Collectors.toSet());
            return humanCareers.contains(career);
        };
    }

    public static Predicate<Human> getHumansFilterByGenre(Genre genre) {
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


    public static Comparator<Human> getHumansComparator(HumanSortType sortType) {
        switch (sortType) {
            case byName:
                return getHumanNameComparator();
            case byPopular:
            default:
                return getHumanPopularComparator().reversed();

        }
    }

    private static Comparator<Human> getHumanNameComparator() {
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

    private static Comparator<Human> getHumanPopularComparator() {
        return (o1, o2) -> {
            Set<Film> humanFilms1 = getHumanFilms(o1);
            Set<Film> humanFilms2 = getHumanFilms(o2);
            Integer size1 = humanFilms1.size();
            Integer size2 = humanFilms2.size();
            return size1.compareTo(size2);
        };
    }

    private static Set<Film> getHumanFilms(Human human) {
        Set<HumanRoleInFilm> humanFilms = human.getHumanRoles();
        return humanFilms.stream().map(
                HumanRoleInFilm::getFilm
        ).collect(Collectors.toSet());
    }


}
