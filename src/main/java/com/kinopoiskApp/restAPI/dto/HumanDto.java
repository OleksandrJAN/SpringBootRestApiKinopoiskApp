package com.kinopoiskApp.restAPI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HumanDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int totalFilms;
    private String image;

    private List<String> humanCareers;

}
