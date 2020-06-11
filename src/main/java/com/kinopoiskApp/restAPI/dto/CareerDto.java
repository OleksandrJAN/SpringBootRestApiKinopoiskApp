package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Career;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class CareerDto {

    private CareerInfo careerInfo;
    private Set<FilmAndHumanInfo> filmsAndHumansInfo;

    @Getter
    @Setter
    private class FilmAndHumanInfo {
        private HumanInfo humanInfo;
        private FilmInfo filmInfo;

        FilmAndHumanInfo(HumanRoleInFilm filmAndHumanInfo) {
            this.humanInfo = new HumanInfo(filmAndHumanInfo.getHuman());
            this.filmInfo = new FilmInfo(filmAndHumanInfo.getFilm());
        }
    }

    public CareerDto(Career career) {
        this.careerInfo = new CareerInfo(career);

        // множество с Film, Human и Career == career
        Set<HumanRoleInFilm> filmAndHumanInfo = career.getHumanRoles();
        this.filmsAndHumansInfo = filmAndHumanInfo.stream().map(
                FilmAndHumanInfo::new
        ).collect(Collectors.toSet());
    }

}
