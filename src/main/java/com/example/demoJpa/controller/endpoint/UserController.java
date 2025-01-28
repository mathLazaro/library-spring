package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.dto.users.UserDTO;
import com.example.demoJpa.controller.dto.users.UserOutputDTO;
import com.example.demoJpa.controller.mapper.UserMapper;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{username}")
    public ResponseEntity<UserOutputDTO> getUser(@PathVariable("username") String username) {
        System.out.println(username);
        Optional<Users> response = userService.getByLogin(username);
        if (response.isEmpty())
            throw new EntityNotFoundException("Username não encontrado");
        UserOutputDTO user = userMapper.toUserOutputDTO(response.get());
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody @Valid UserDTO userDTO) {
        Integer id = userService.saveUser(userDTO);
        return ResponseEntity.created(generateUri(id)).build();
    }

    @PutMapping
    // Só o usuário pode mudar suas próprias propriedades
    public ResponseEntity<Void> putUser(@RequestBody UserDTO userDTO) {
        userService.changeUserProperties(userDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
