package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Country;
import com.kinopoiskApp.restAPI.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

    @Autowired
    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }


    public List<String> getCountriesNames() {
        return countryRepo.findAll()
                .stream()
                .map(Country::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    public Country getCountryByName(String countryName) {
        return countryRepo.findByName(countryName);
    }

}
