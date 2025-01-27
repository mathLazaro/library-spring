package com.example.demoJpa.validator;

import com.example.demoJpa.domain.RoleUser;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UsersRepository repository;

    public void verifyAdmin(Integer id) {

        Users user = repository.findById(id).orElse(null);
        verifyNotFound(user);
        verifyRole(user);
    }


    private void verifyRole(Users user) {

        if (user.getRoleUser() != RoleUser.ADMIN)
            throw new PermissionDeniedDataAccessException("Esta operação só pode ser realizada por usuário administrador", new Exception());
    }

    private void verifyNotFound(Users user) {

        if (user == null)
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

    }
}
