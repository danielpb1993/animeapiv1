package com.example.anime.repository;

import com.example.anime.domain.model.Genre;
import com.example.anime.domain.model.projections.ProjectionGenre;
import com.example.anime.domain.model.projections.ProjectionGenreDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    List<ProjectionGenre>findBy();
    ProjectionGenreDetails findByGenreid(UUID id);
}
