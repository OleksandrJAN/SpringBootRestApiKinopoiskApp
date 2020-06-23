package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Country;
import com.kinopoiskApp.restAPI.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

    @Autowired
    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }


    public Stream<Country> getCountries() {
        return countryRepo.findAll().stream();
    }

    public Country getCountryByName(String countryName) {
        return countryRepo.findByName(countryName);
    }

    public List<String> getCountriesNames(Stream<Country> countries) {
        return countries
                .map(Country::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
