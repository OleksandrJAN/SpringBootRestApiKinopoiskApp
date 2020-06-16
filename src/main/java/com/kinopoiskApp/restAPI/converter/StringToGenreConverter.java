package com.kinopoiskApp.restAPI.converter;

import com.kinopoiskApp.restAPI.domain.Genre;
import com.kinopoiskApp.restAPI.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGenreConverter implements Converter<String, Genre> {

    private static GenreRepo genreRepo;

    @Autowired
    public void setGenreRepo(GenreRepo genreRepo) {
        StringToGenreConverter.genreRepo = genreRepo;
    }

    @Override
    public Genre convert(String s) {
        return genreRepo.findByName(s);
    }
}
