package com.example.anime.repository;

import com.example.anime.domain.model.Genre;
import com.example.anime.domain.model.projections.ProjectionGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    List<ProjectionGenre>findBy();
    <T> T findByGenreid(UUID id, Class<T> type);}
