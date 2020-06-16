package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanSortType;
import com.kinopoiskApp.restAPI.dto.FilmInfo;
import com.kinopoiskApp.restAPI.dto.HumanDto;
import com.kinopoiskApp.restAPI.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("humans/{human:[\\d]+}/roles")
    public ResponseEntity<Map<String, List<FilmInfo>>> getHumanRoles(
            @PathVariable Human human,
            @RequestParam HumanSortType sort,
            @RequestParam(required = false) Genre genre
    ) {
        if (human != null) {
            Map<String, List<FilmInfo>> humanRoles = humanService.getHumanRoles(human, sort, genre);
            return new ResponseEntity<>(humanRoles, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("humans/{human:[\\d]+}/genres")
    public ResponseEntity<List<String>> getHumanGenres(@PathVariable Human human) {
        if (human != null) {
            List<String> humanGenres = humanService.getHumanGenres(human);
            return new ResponseEntity<>(humanGenres, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
