package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.GenericController;
import com.example.demoJpa.controller.dto.author.AuthorDTO;
import com.example.demoJpa.domain.RoleUser;
import com.example.demoJpa.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor

public class AuthorController implements GenericController {

    private final AuthorService service;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.getAuthorById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<List<AuthorDTO>> getAllAuthorsByNameOrNationality(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        return new ResponseEntity<>(service.searchAuthorsByNameOrNationality(name, nationality), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> postAuthor(
            @Valid @RequestBody AuthorDTO author) {

        Integer id = service.persistAuthor(author);

        return ResponseEntity.created(generateUri(id)).build();
    }


    @PutMapping("{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> putAuthor(
            @Valid @RequestBody AuthorDTO author,
            @PathVariable("authorId") Integer authorId) {

        service.updateAuthor(author, authorId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("authorId") Integer authorId) {

        service.deleteAuthorById(authorId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
