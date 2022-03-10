package com.example.anime.controller;



import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.model.projections.ProjectionGenre;
import com.example.anime.domain.model.projections.ProjectionGenreDetails;
import com.example.anime.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllGenre() {
        List<ProjectionGenre> genreList = genreRepository.findBy();
        return ResponseEntity.ok().body(ListResult.list(genreList));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenre(@PathVariable UUID id) {
        ProjectionGenreDetails genre = genreRepository.findByGenreid(id, ProjectionGenreDetails.class);
        if (genre != null) {
            return ResponseEntity.ok().body(genre);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l' genre amd id %s", id)));
    }

}