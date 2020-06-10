package com.kinopoiskApp.restAPI.service;

import com.kinopoiskApp.restAPI.domain.Human;
import com.kinopoiskApp.restAPI.repo.HumanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {

    private final HumanRepo humanRepo;

    @Autowired
    public HumanService(HumanRepo humanRepo) {
        this.humanRepo = humanRepo;
    }

    public List<Human> getHumans() {
        return humanRepo.findAll();
    }
}
