package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HumanController {

    private final HumanService humanService;

    @Autowired
    public HumanController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping("humans")
    public ResponseEntity<List<Human>> getHumans() {
        return new ResponseEntity<>(humanService.getHumans(), HttpStatus.OK);
    }
}
