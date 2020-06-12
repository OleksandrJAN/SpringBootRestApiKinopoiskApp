package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class FilmDto {

    private Long id;
    private String filmName;
    private String slogan;
    private String country;
    private int year;
    private String annotation;
    private String image;

    private Set<Genre> filmGenres;
    private Set<HumanRoleInFilmDto> humanRoles;

    @Getter
    @Setter
    private class HumanRoleInFilmDto {
        private HumanInfo humanInfo;
        private CareerInfo humanCareerInfo;

        HumanRoleInFilmDto(HumanRoleInFilm filmRoles) {
            Human human = filmRoles.getHuman();
            this.humanInfo = new HumanInfo(human);
            this.humanCareerInfo = new CareerInfo(filmRoles.getCareer());
        }
    }


    public FilmDto(Film film) {
        this.id = film.getId();
        this.filmName = film.getFilmName();
        this.slogan = film.getSlogan();
        this.country = film.getCountry();
        this.year = film.getYear();
        this.annotation = film.getAnnotation();
        this.image = film.getImage();
        this.filmGenres = film.getFilmGenres();
        Set<HumanRoleInFilm> humanRoles = film.getHumanRoles();

        this.humanRoles = humanRoles.stream().map(
                HumanRoleInFilmDto::new
        ).collect(Collectors.toSet());
    }
}
