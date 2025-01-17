package com.example.demoJpa.controller;

import com.example.demoJpa.dto.AuthorDTO;
import com.example.demoJpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Integer id) {

        try {
            return new ResponseEntity<>(service.getAuthorById(id), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthorsByNameOrNationality(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {

        try {
            return new ResponseEntity<>(service.searchAuthorsByNameOrNationality(name, nationality), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    @PostMapping("{userId}")
    public ResponseEntity<Object> postAuthor(
            @RequestBody AuthorDTO author,
            @PathVariable("userId") Integer user) {

        try {
            Integer id = service.persistAuthor(author, user);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(id).toUri();


            return ResponseEntity.created(uri).build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }


    @PutMapping("{authorId}/user/{userId}")
    public ResponseEntity<String> putAuthor(
            @RequestBody AuthorDTO author,
            @PathVariable("authorId") Integer authorId,
            @PathVariable("userId") Integer userId) {

        try {
            service.updateAuthor(author, authorId, userId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @DeleteMapping("{authorId}/user/{userId}")
    public ResponseEntity<String> deleteAuthor(
            @PathVariable("authorId") Integer authorId,
            @PathVariable("userId") Integer userId) {

        try {
            service.deleteAuthorById(authorId, userId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
