package com.example.demoJpa.controller;

import com.example.demoJpa.domain.UserApplication;
import com.example.demoJpa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {

        this.repository = repository;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserApplication> getUser(@PathVariable("id") Integer id) {

        Optional<UserApplication> response = repository.findById(id);
        return response.isPresent() ?
                new ResponseEntity<>(response.get(), HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> postUser(@RequestBody UserApplication user) {

        UserApplication response = repository.save(user);
        return response.getId() == null ?
                new ResponseEntity<>("Não foi possível persistir o usuário", HttpStatus.EXPECTATION_FAILED) :
                new ResponseEntity<>("Usuário salvo", HttpStatus.OK);
    }
}
