package com.example.anime;

import com.example.anime.domain.dto.AnimeData;
import com.example.anime.domain.dto.RecommendedResponse;
import com.example.anime.domain.model.Anime;
import com.example.anime.domain.model.Genre;
import com.example.anime.domain.model.Recommended;
import com.example.anime.repository.AnimeRepository;
import com.example.anime.repository.GenreRepository;
import com.example.anime.repository.RecommendedRepository;
import com.example.anime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RecommendedService {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private RecommendedRepository recommendedRepository;

    public List<?> getRecommended(){
        List<Recommended> recommendedList = recommendedRepository.findAll();

        Set<String> labels = recommendedList.stream()
                .map(recommended ->
                        genreRepository.findByGenreid(recommended.genreid, Genre.class).label)
                .collect(Collectors.toSet());
        List<RecommendedResponse> recommendedResponses = labels.stream()
                .map(recommended -> {
                    RecommendedResponse rr = new RecommendedResponse();
                    rr.genre = recommended;
                    return rr;
                }).collect(Collectors.toList());
        for (RecommendedResponse rr : recommendedResponses ) {
            for (Recommended recommended: recommendedList ) {
                String label =  genreRepository.findByGenreid(recommended.genreid, Genre.class).label;
                if (label.equals(rr.genre)){
                    Anime anime = animeRepository.findByAnimeid(recommended.animeid, Anime.class);
                    rr.animes.add(new AnimeData(anime.name, anime.type));
                }
            }
        }

        return  recommendedResponses;

    }

    public boolean isValid(UUID animeID){
        Anime anime = animeRepository.findById(animeID).orElse(null);
        return anime != null;
    }

    public void save(UUID animeId,  UUID userId) {
        List<UUID> genres = animeRepository.findByAnimeid(animeId, Anime.class).genres.stream()
                .map(genre -> genre.genreid)
                .collect(Collectors.toList());

        Recommended recommended = new Recommended();
        genres.forEach(genre -> {
                    recommended.genreid = genre;
                    recommended.userid = userId;
                    recommended.animeid = animeId;
                    recommendedRepository.save(recommended);
                }
        );
    }

}