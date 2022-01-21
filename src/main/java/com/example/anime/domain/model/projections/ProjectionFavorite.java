package com.example.anime.domain.model.projections;

import java.util.Set;


public interface ProjectionFavorite {
    Set<ProjectionAnimeFavorited> getFavorites();
}