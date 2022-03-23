package com.example.anime;

import com.example.anime.domain.model.projections.ProjectionRating;
import com.example.anime.repository.AnimeRepository;
import com.example.anime.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class AnimeService {
    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    RatingRepository ratingRepository;

    public List<ProjectionRating> getRatingAnime(){
        return ratingRepository.findBy();
    }
}
