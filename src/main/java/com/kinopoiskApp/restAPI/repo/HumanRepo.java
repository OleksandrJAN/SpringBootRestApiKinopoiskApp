package com.kinopoiskApp.restAPI.repo;

import com.kinopoiskApp.restAPI.domain.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepo extends JpaRepository<Human, Long> {
}
