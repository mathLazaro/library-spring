package com.example.demoJpa.validator;

import jakarta.persistence.EntityNotFoundException;

public class EntityValidator<T> {

    public void verifyNotFound(T entity) {

        if (entity == null)
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

    }
}
