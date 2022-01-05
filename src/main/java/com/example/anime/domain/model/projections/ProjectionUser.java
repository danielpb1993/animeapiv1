package com.example.anime.domain.model.projections;

//import com.example.animeapi.domains.models.Favorite;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder({"userid", "username"})
public interface ProjectionUser {

    UUID getUserid();
    String getUsername();

//    @JsonIgnoreProperties("favoriteby")
//    Set<ProjectionFavorite> getFavorites();

}