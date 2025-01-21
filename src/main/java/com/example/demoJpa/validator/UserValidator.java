package com.example.demoJpa.validator;

import com.example.demoJpa.domain.RoleUser;
import com.example.demoJpa.domain.UserApplication;
import com.example.demoJpa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;

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
            throw new PermissionDeniedDataAccessException("Esta operação só pode ser realizada por usuário administrador", new Exception());
    }

    private void verifyNotFound(UserApplication user) {

        if (user == null)
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

    }
}
