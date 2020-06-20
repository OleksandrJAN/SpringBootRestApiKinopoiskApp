package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Genre;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GenreInfo {

    private Long id;
    private String name;

    public GenreInfo(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
