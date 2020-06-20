package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Career;
import com.kinopoiskApp.restAPI.repo.CareerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerService {

    private final CareerRepo careerRepo;

    @Autowired
    public CareerService(CareerRepo careerRepo) {
        this.careerRepo = careerRepo;
    }

    public List<Career> getCareers() {
        return careerRepo.findAll();
    }

    public List<String> getSortedCareersNames(Collection<Career> careers) {
        return careers.stream().map(
                Career::getName
        ).sorted().collect(Collectors.toList());
    }

    public Career getCareerByName(String careerName) {
        return careerRepo.findByName(careerName);
    }
}
