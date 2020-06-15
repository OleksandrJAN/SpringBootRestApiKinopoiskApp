package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class HumanDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int totalFilms;
    private String image;

    @Getter
    @Setter
    private class FilmAndCareerInfo {
        private FilmInfo filmInfo;
        private CareerInfo careerInfo;

        FilmAndCareerInfo(HumanRoleInFilm filmAndCareerInfo) {
            this.filmInfo = new FilmInfo(filmAndCareerInfo.getFilm());
            this.careerInfo = new CareerInfo(filmAndCareerInfo.getCareer());
        }
    }

    public HumanDto(Human human) {
        this.id = human.getId();
        this.firstName = human.getFirstName();
        this.lastName = human.getLastName();
        this.birthDate = human.getBirthDate();
        this.totalFilms = human.getTotalFilms();
        this.image = human.getImage();

        // множество с Film, Human == human и Career
        Set<HumanRoleInFilm> filmAndCareerInfo = human.getHumanRoles();
        this.filmAndCareerInfo = filmAndCareerInfo.stream().map(
                FilmAndCareerInfo::new
        ).collect(Collectors.toSet());
    }
}
