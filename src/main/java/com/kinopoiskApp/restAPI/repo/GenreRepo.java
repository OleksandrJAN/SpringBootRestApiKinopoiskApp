package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}
