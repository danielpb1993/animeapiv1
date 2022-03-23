package com.example.anime.services;

import com.example.anime.domain.dto.RatingRequest;
import com.example.anime.domain.model.Rating;
import com.example.anime.domain.model.projections.ProjectionRating;
import com.example.anime.repository.AnimeRepository;
import com.example.anime.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    RatingRepository ratingRepository;

    public List<ProjectionRating> getRatingAnime(){
        return ratingRepository.findBy();
    }

    public boolean existAnime(String name) {
        return animeRepository.findByname(name) == null;
    }

    public void AddRatingAnime(RatingRequest requestRating, String username) {
        Rating rating = new Rating();
        rating.anime = requestRating.anime;
        rating.score = requestRating.score;
        rating.username = username;
        ratingRepository.save(rating);
    }
}
