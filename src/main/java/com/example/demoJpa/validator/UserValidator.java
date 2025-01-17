package com.example.demoJpa.validator;

import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.RoleUser;
import com.example.demoJpa.domain.UserApplication;
import com.example.demoJpa.repository.AuthorRepository;
import com.example.demoJpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void verifyAdmin(Integer id) {

        UserApplication user = repository.findById(id).orElse(null);
        verifyNotFound(user);
        verifyRole(user);
    }


    private void verifyRole(UserApplication user) {

        if (user.getRoleUser() != RoleUser.ADMIN)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Esta operação só pode ser realizada por usuário administrador");
    }

    private void verifyNotFound(UserApplication user) {

        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id de usuário não encontrado no banco de dados");

    }
}
