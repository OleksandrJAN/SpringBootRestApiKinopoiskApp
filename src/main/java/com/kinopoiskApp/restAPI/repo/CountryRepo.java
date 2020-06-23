package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
