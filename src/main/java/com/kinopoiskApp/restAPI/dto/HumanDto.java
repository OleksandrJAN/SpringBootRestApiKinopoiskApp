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

    private Set<CareerInfo> careersInfo;

    public HumanDto(Human human) {
        this.id = human.getId();
        this.firstName = human.getFirstName();
        this.lastName = human.getLastName();
        this.birthDate = human.getBirthDate();
        this.totalFilms = human.getTotalFilms();
        this.image = human.getImage();

        // множество с Film, Human == human и Career
        Set<HumanRoleInFilm> humanRoles = human.getHumanRoles();
        this.careersInfo = humanRoles.stream().map(
                HumanRoleInFilm::getCareer
        ).distinct().map(
                CareerInfo::new
        ).collect(Collectors.toSet());
    }
}
