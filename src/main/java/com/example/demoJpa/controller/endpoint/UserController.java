package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.GenericController;
import com.example.demoJpa.controller.dto.users.UserDTO;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody @Valid UserDTO userDTO) {
        Integer id = userService.saveUser(userDTO);

        return ResponseEntity.created(generateUri(id)).build();
    }
}
