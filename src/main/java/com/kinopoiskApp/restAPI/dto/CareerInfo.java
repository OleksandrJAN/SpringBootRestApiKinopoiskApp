package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Career;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CareerInfo {

    private Long id;
    private String name;

    CareerInfo(Career career) {
        this.id = career.getId();
        this.name = career.getName();
    }
}
