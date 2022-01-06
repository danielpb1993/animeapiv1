package com.example.anime.controller;

import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.model.Anime;
import com.example.anime.domain.model.projections.ProjectionAnime;
import com.example.anime.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animes")
public class AnimeController {
    @Autowired
    private AnimeRepository animeRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllAnime() {
        List<ProjectionAnime> animeList = animeRepository.findBy();
        return ResponseEntity.ok().body(ListResult.list(animeList));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnime(@PathVariable UUID id) {
        ProjectionAnime anime = animeRepository.findByAnimeid(id);
        if (anime != null) {
            return ResponseEntity.ok().body(anime);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l' anime amd id %s", id)));
    }

    @PostMapping("/")
    public ResponseEntity<?> addAnime(@RequestBody Anime anime) {
        if (animeRepository.findByname(anime.name) == null)
            return ResponseEntity.ok().body(animeRepository.save(anime));

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(DisplayMessage.message(String.format("Ja existeix un anime amb el nom '%s' ", anime.name)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnime(@PathVariable UUID id) {
        Anime anime = animeRepository.findById(id).orElse(null);
        if (anime != null) {
            animeRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(DisplayMessage.message(String.format("S'ha eliminat l'anime amd id '%s'", id)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l' anime amd id %s", id)));
    }

}