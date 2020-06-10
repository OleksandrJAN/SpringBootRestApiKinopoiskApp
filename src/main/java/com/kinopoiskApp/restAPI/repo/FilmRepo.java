package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film, Long> {
}
