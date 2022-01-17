package com.example.anime.domain.model.projections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;


@JsonPropertyOrder({"name", "description", "type"})
public interface ProjectionFavorite {
    String getName();
    String getType();

    @JsonIgnoreProperties({"animes", "authorid","imageurl"})
    Set<ProjectionAuthor> getAuthors();


}