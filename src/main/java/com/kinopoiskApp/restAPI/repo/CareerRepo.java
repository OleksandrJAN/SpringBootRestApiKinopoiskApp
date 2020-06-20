package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepo extends JpaRepository<Career, Long> {
    Career findByName(String name);
}
