package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Career;
import com.kinopoiskApp.restAPI.dto.CareerDto;
import com.kinopoiskApp.restAPI.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CareerController {

    private final CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("/careers/{career:[\\d]+}")
    public ResponseEntity<CareerDto> getCareer(@PathVariable Career career) {
        if (career != null) {
            CareerDto careerDto = new CareerDto(career);
            return new ResponseEntity<>(careerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
