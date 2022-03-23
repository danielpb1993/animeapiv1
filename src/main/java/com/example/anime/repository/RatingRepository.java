package com.example.anime.repository;

import com.example.anime.domain.model.Rating;
import com.example.anime.domain.model.projections.ProjectionRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    List<ProjectionRating> findBy();
}
