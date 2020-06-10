package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FilmDto {

    private Long id;

    @NotBlank
    @Length(max = 127)
    private String filmName;

    @NotBlank
    @Length(max = 127)
    private String slogan;

    @NotBlank
    @Length(max = 127)
    private String country;

    @Positive
    private int year;

    @NotBlank
    @Length(max = 2047)
    private String annotation;

    private List<Genre> filmGenres;

    Set<HumanRoleInFilmDto> humanRoles;

    @Getter
    @Setter
    private class HumanRoleInFilmDto {
        private Long humanId;
        private String humanFirstName;
        private String humanLastName;
        private Career career;

        public HumanRoleInFilmDto(HumanRoleInFilm filmRoles) {
            this.humanId = filmRoles.getId().getHumanId();
            Human human = filmRoles.getHuman();
            this.humanFirstName = human.getFirstName();
            this.humanLastName = human.getLastName();
            this.career = filmRoles.getCareer();
        }
    }


    public FilmDto(Film film) {
        this.id = film.getId();
        this.filmName = film.getFilmName();
        this.slogan = film.getSlogan();
        this.country = film.getCountry();
        this.year = film.getYear();
        this.annotation = film.getAnnotation();
        this.filmGenres = film.getFilmGenres();
        Set<HumanRoleInFilm> humanRoles = film.getHumanRoles();

        this.humanRoles = humanRoles.stream().map(
                HumanRoleInFilmDto::new
        ).collect(Collectors.toSet());
    }
}
