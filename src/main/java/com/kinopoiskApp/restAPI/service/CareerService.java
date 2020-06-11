package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Career;
import com.kinopoiskApp.restAPI.repo.CareerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
