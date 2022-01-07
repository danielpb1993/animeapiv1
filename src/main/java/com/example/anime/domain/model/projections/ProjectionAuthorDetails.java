package com.example.anime.domain.model.projections;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder({"authorid", "name", "imageurl"})
public interface ProjectionAuthorDetails {

    UUID getAuthorid();
    String getName();
    String getImageurl();
    @JsonIgnoreProperties("authors")
    Set<ProjectionAnime> getAnimes();
}