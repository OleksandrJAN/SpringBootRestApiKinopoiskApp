package com.kinopoiskApp.restAPI.dto;

import com.kinopoiskApp.restAPI.domain.Human;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HumanInfo {

    private Long humanId;
    private String humanFirstName;
    private String humanLastName;

    HumanInfo(Human human) {
        this.humanId = human.getId();
        this.humanFirstName = human.getFirstName();
        this.humanLastName = human.getLastName();
    }

}