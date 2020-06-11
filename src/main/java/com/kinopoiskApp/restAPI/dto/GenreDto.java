package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GenreDto {
    //подумать еще над жанрами и сортировкой

    private Long id;
    private String name;

    //в будущем сортировка фильмов по рейтингу
    private Set<FilmInfo> filmsInfo;
    private List<HumanInfo> humansInfo;


    public GenreDto(Genre genre, List<Human> popularHumanInGenre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.filmsInfo = genre.getFilms().stream().map(
                FilmInfo::new
        ).collect(Collectors.toSet());

        this.humansInfo = popularHumanInGenre.stream().map(
                HumanInfo::new
        ).collect(Collectors.toList());
    }
}
