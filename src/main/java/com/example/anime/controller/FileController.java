package com.example.anime.controller;

import com.example.anime.domain.dto.DisplayMessage;
import com.example.anime.domain.dto.FileResult;
import com.example.anime.domain.dto.ListResult;
import com.example.anime.domain.model.File;
import com.example.anime.domain.model.projections.ProjectionFile;
import com.example.anime.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllFile() {
        List<ProjectionFile> fileList = fileRepository.findBy();
        return ResponseEntity.ok().body(ListResult.list(fileList));

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable UUID id) {
        File file = fileRepository.findById(id).orElse(null);
        if (file != null)
            return ResponseEntity.ok().contentType(MediaType.valueOf(file.contenttype))
                    .contentLength(file.data.length)
                    .body(file.data);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s'ha trobat l'arxiu amd id '%s'", id)));
    }

    @PostMapping("/")
    public ResponseEntity<?> addFile(@RequestParam("file") MultipartFile uploadedFile) {
        try {
            File file = new File();
            file.contenttype = uploadedFile.getContentType();
            file.data = uploadedFile.getBytes();
            fileRepository.save(file);
            return ResponseEntity.ok().body(FileResult.file(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        File file = fileRepository.findById(id).orElse(null);
        if (file != null) {
            fileRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(DisplayMessage.message(String.format("S'ha eliminat l'arxiu amd id '%s'", id)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DisplayMessage.message(String.format("No s'ha trobat l'arxiu amd id %s", id)));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllUser() {
        fileRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}