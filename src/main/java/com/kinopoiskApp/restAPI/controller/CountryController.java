package com.kinopoiskApp.restAPI.controller;

import com.kinopoiskApp.restAPI.domain.Country;
import com.kinopoiskApp.restAPI.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("countries")
    public ResponseEntity<List<String>> getCountries() {
        Stream<Country> countries = countryService.getCountries();
        List<String> countriesNames = countryService.getCountriesNames(countries);
        return new ResponseEntity<>(countriesNames, HttpStatus.OK);
    }
}
