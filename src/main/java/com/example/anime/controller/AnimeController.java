package com.example.anime.controller;

import com.example.anime.services.AnimeService;
import com.example.anime.services.RecommendedService;
import com.example.anime.services.UserService;
import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.dto.RatingRequest;
import com.example.anime.domain.model.Anime;
import com.example.anime.domain.model.projections.ProjectionAnime;
import com.example.anime.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.example.anime.domain.model.projections.ProjectionAnimeShort;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animes")
public class AnimeController {
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RecommendedService recommendedService;
    @Autowired
    AnimeService animeService;

    @GetMapping("/")
    public ResponseEntity<?> getAllAnime() {
        List<ProjectionAnime> animeList = animeRepository.findBy();
        return ResponseEntity.ok().body(ListResult.list(animeList));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnime(@PathVariable UUID id) {
        ProjectionAnime anime = animeRepository.findByAnimeid(id, ProjectionAnime.class);
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


    @GetMapping("/recommended/")
    public ResponseEntity<?> getRecommended(){
        return ResponseEntity.ok().body(ListResult.list(recommendedService.getRecommended()));
    }

    @PostMapping("/recommended/")
    public ResponseEntity<?> addRecommended(@RequestBody Anime anime, Authentication authentication){

        if (userService.ifExists(authentication.getName())){
            if(recommendedService.isValid(anime.animeid)){
                UUID userid = userService.getUserId(authentication.getName());
                recommendedService.save(anime.animeid, userid);
                String animeName = animeRepository.findByAnimeid(anime.animeid, ProjectionAnimeShort.class).getName();
                return ResponseEntity.ok().body(DisplayMessage.message(String.format("Added '%s' to recommended animes", animeName)));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DisplayMessage.message("Incorrect data"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(DisplayMessage.message("Authentication required"));
    }

    @GetMapping("/rating/")
    public ResponseEntity<?> getAnimeRating(){
        return ResponseEntity.ok().body(ListResult.list(animeService.getRatingAnime()));
    }

    @PostMapping("/rating/")
    public ResponseEntity<?> addAnimeRating(@RequestBody RatingRequest requestRating, Authentication authentication){
        if(!animeService.existAnime(requestRating.anime)){
            animeService.AddRatingAnime(requestRating, authentication.getName());
            return ResponseEntity.ok().body(DisplayMessage.message(String.format("Added rating to anime '%s'", requestRating.anime)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DisplayMessage.message(String.format("The anime '%s' doesn't exist", requestRating.anime)));

    }
}