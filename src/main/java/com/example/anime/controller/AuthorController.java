package com.example.anime.controller;

import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.model.Author;
import com.example.anime.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository AuthorRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllAuthor() {
        List<Author> animeList = AuthorRepository.findAll();
        return ResponseEntity.ok().body(ListResult.list(animeList));

    }



}
