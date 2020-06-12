package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import com.kinopoiskApp.restAPI.dto.HumanInfo;
import com.kinopoiskApp.restAPI.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public List<Film> getFilms() {
        return filmRepo.findAll();
    }

    public Map<String, List<HumanInfo>> getFilmCast(Film film) {
        Map<String, List<HumanInfo>> filmCastMap = new HashMap<>();
        Set<HumanRoleInFilm> humanRoles = film.getHumanRoles();

        humanRoles.forEach(
                humanRoleInFilm -> {
                    Human human = humanRoleInFilm.getHuman();
                    String careerName = humanRoleInFilm.getCareer().getName();
                    if (!filmCastMap.containsKey(careerName)) {
                        filmCastMap.put(careerName, new ArrayList<>());
                    }
                    filmCastMap.get(careerName).add(new HumanInfo(human));
                }
        );

        return filmCastMap;
    }

}
