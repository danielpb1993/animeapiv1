package com.example.anime.domain.model.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder({"animeid", "name", "imageurl"})
public interface ProjectionFavorite {
    UUID getAnimeid();
    String getName();
    String getImageurl();
}