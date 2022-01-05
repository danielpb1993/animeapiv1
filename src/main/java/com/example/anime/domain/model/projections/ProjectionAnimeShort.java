//package com.example.anime.domain.model.projections;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//import java.util.Set;
//import java.util.UUID;
//
//@JsonPropertyOrder({"animeid", "name", "type", "imageurl"})
//public interface ProjectionAnimeShort {
//    UUID getAnimeid();
//    String getName();
//    String getType();
//    String getImageurl();
//
//    @JsonIgnoreProperties({"animes", "imageurl"})
//    Set<ProjectionAuthor> getAuthors();
//
//
//}