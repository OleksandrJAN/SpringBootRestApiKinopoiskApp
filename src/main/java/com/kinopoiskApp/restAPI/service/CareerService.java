package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Career;
import com.kinopoiskApp.restAPI.domain.Film;
import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.domain.HumanRoleInFilm;
import com.kinopoiskApp.restAPI.repo.CareerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CareerService {

    private final CareerRepo careerRepo;

    @Autowired
    public CareerService(CareerRepo careerRepo) {
        this.careerRepo = careerRepo;
    }


    public Stream<Career> getCareers() {
        return careerRepo.findAll().stream();
    }

    public Career getCareerByName(String careerName) {
        return careerRepo.findByName(careerName);
    }

    public List<String> getCareersNames(Stream<Career> careers) {
        return careers
                .map(Career::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    public Stream<Career> getHumanCareersInFilm(Human human, Film film) {
        return film.getHumanRoles().stream()
                .filter(humanRoleInFilm -> humanRoleInFilm.getHuman().equals(human))
                .map(HumanRoleInFilm::getCareer);
    }

}
