package com.kinopoiskApp.restAPI.dto.factory;

import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HumanDtoFactory {

    public static HumanDto createHumanDto(Human human) {
        HumanDto humanDto = new HumanDto();
        humanDto.setId(human.getId());
        humanDto.setFirstName(human.getFirstName());
        humanDto.setLastName(human.getLastName());
        humanDto.setBirthDate(human.getBirthDate());
        humanDto.setTotalFilms(human.getTotalFilms());
        humanDto.setImage(human.getImage());

        List<String> careers = human.getHumanRoles().stream()
                .map(humanRoleInFilm -> humanRoleInFilm.getCareer().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        humanDto.setHumanCareers(careers);

        return humanDto;
    }
}
