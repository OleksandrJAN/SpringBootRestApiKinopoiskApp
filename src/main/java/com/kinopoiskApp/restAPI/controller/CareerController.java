package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    private final CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("careers")
    public ResponseEntity<List<String>> getCareers() {
        List<String> careersNames = careerService.getCareersNames();
        return new ResponseEntity<>(careersNames, HttpStatus.OK);
    }

}
