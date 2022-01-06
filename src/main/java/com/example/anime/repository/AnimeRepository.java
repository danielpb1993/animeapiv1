package com.example.anime.repository;


import com.example.anime.domain.model.Anime;
import com.example.anime.domain.model.projections.ProjectionAnime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {

    Anime findByname(String name);
    List<ProjectionAnime>findBy();
    ProjectionAnime findByAnimeid(UUID id);
}