package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HumanController {

    private final HumanService humanService;

    @Autowired
    public HumanController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping("humans")
    public ResponseEntity<List<HumanDto>> getHumans() {
        List<Human> humans = humanService.getHumans();
        List<HumanDto> humanDtos = humans.stream().map(
                HumanDto::new
        ).collect(Collectors.toList());
        return new ResponseEntity<>(humanDtos, HttpStatus.OK);
    }

    @GetMapping("humans/{human:[\\d]+}")
    public ResponseEntity<HumanDto> getHuman(@PathVariable Human human) {
        if (human != null) {
            HumanDto humanDto = new HumanDto(human);
            return new ResponseEntity<>(humanDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
