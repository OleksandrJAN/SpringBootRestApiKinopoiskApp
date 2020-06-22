package com.kinopoiskApp.restAPI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilmDto {

    private Long id;
    private String filmName;
    private String slogan;
    private String country;
    private int year;
    private String annotation;
    private String image;

    private List<String> filmGenres;

}
