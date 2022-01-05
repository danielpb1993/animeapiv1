//package com.example.anime.domain.model.projections;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//import java.util.Set;
//import java.util.UUID;
//
//@JsonPropertyOrder({"genreid", "label"})
//public interface ProjectionGenre {
//
//    UUID getGenreid();
//    String getLabel();
//
//    @JsonIgnoreProperties({"genres", "description", "year_release"})
//    Set<ProjectionAnimeShort> getAnimes();
//
//}