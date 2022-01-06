package com.example.anime.controller;

import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.model.Author;
import com.example.anime.domain.model.projections.ProjectionAuthor;
import com.example.anime.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository AuthorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable UUID id) {
        ProjectionAuthor author = AuthorRepository.findByAuthorid(id, ProjectionAuthor.class);
        if (author != null) {
            return ResponseEntity.ok().body(author);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s 'ha trobat l' author amd id %s", id)));
    }



}
