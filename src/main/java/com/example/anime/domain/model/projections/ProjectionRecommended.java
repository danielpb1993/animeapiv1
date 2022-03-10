package com.example.anime.domain.model.projections;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder({"label"})
public interface ProjectionRecommended {

    String getLabel();

    @JsonIgnoreProperties({"animeid", "authors"})
    Set<ProjectionAnimeShort> getAnimes();
}
