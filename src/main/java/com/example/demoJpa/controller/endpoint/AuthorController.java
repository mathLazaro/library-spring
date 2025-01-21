package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.dto.author.AuthorDTO;
import com.example.demoJpa.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.getAuthorById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthorsByNameOrNationality(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        return new ResponseEntity<>(service.searchAuthorsByNameOrNationality(name, nationality), HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<Void> postAuthor(
            @Valid @RequestBody AuthorDTO author,
            @PathVariable("userId") Integer user) {

        Integer id = service.persistAuthor(author, user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @PutMapping("{authorId}/user/{userId}")
    public ResponseEntity<Void> putAuthor(
            @Valid @RequestBody AuthorDTO author,
            @PathVariable("authorId") Integer authorId,
            @PathVariable("userId") Integer userId) {

        service.updateAuthor(author, authorId, userId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{authorId}/user/{userId}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("authorId") Integer authorId,
            @PathVariable("userId") Integer userId) {

        service.deleteAuthorById(authorId, userId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
