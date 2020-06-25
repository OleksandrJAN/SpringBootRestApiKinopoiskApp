package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Country;
import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film, Long> {
    Page<Film> findByFilmCountries(Country country, Pageable pageable);

    Page<Film> findByFilmGenres(Genre genre, Pageable pageable);

    Page<Film> findByFilmCountriesAndFilmGenres(Country country, Genre genre, Pageable pageable);

}
