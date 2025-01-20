package com.example.demoJpa.validator;

import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository repository;


    public void verifyDuplicatedAuthor(Author author) {

        boolean condition = repository.existsAuthorByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality());
        if (condition)
            throw new DuplicateKeyException("Já existe um autor cadastrado com esses parâmetros");
    }

    public void verifyReferencedAuthor(Author author) {

        List<Book> bookList = author.getBookList();
        if (!bookList.isEmpty())
            throw new DataIntegrityViolationException("O autor ainda possui livros e não pode ser deletado");
    }

    public void verifyNotFound(Author author) {

        if (author == null)
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

    }
}
