package com.example.anime.controller;

import com.example.anime.UserService;
import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.dto.UserResult;
import com.example.anime.domain.model.Favorite;
import com.example.anime.domain.model.User;
import com.example.anime.domain.model.projections.ProjectionFavorite;
import com.example.anime.domain.model.projections.ProjectionUser;
import com.example.anime.domain.model.projections.ProjectionUserDetail;
import com.example.anime.repository.AnimeRepository;
import com.example.anime.repository.FavoriteRepository;
import com.example.anime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {
        List<ProjectionUser> userList = userRepository.findBy();
        return ResponseEntity.ok().body(ListResult.list(userList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok().body(ListResult.list(userRepository.findByUserid(id, ProjectionUserDetail.class)));
    }


    @GetMapping("/favorites")
    public ResponseEntity<?>getFavorite(Authentication authentication){
        UUID userID = userRepository.findByUsername(authentication.getName()).userid;
        List<Favorite> favorites = favoriteRepository.findByUserid(userID);
        List<ProjectionFavorite> favoritesAnime = favorites
                .stream()
                .map(f -> animeRepository.findByAnimeid(f.animeid, ProjectionFavorite.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ListResult.list(favoritesAnime));
    }


    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {

        if (userRepository.findByUsername(newUser.username) == null) {
            User user = new User();
            user.username = newUser.username;
            user.password = passwordEncoder.encode(newUser.password);
            user.enabled = true;
            userRepository.save(user);
            UserResult userResponse = UserResult.user(user);
            return ResponseEntity.ok().body(userResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(DisplayMessage.message(String.format("Ja existeix un usuari amb el nom '%s'", newUser.username)));
    }

    @PostMapping("/favorites")
    public ResponseEntity<?> addFavorite(@RequestBody Favorite favorite, Authentication authentication) {
        UUID userID = userRepository.findByUsername(authentication.getName()).userid;
        if (animeRepository.findByAnimeid(favorite.animeid, UUID.class) != null){
            favorite.userid =  userID;
            favoriteRepository.save(favorite);
            return ResponseEntity.ok().body(favorite);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l'anime amd id '%s'", favorite.animeid)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(DisplayMessage.message( String.format("S'ha eliminat l'usuari amd id '%s'", id)));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s'ha trobat l'usuari amd id '%s'", id)));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllUser() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable UUID id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());

        if (user != null) {
            Favorite favorite = new Favorite();
            favorite.userid =  userService.getUserId(authentication.getName());            favorite.animeid = id;
            favoriteRepository.delete(favorite);
            return ResponseEntity.ok()
                    .body(DisplayMessage.message(String.format("S'ha eliminat l'anime amd id '%s' dels teus favorits", id)));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l'id '%s'", id)));
    }
}