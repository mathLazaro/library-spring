package com.example.demoJpa.validator;

import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class AuthorValidator {

    private Author author;
    private final AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {

        this.repository = repository;
    }

    public void runValidation(Author author,
                              List<Runnable> listValidation) {

        this.author = author;

        try {
            listValidation.forEach(Runnable::run);
        } finally {
            this.author = null;
        }
    }


    public void verifyFields() {

        if (author.getName() == null || author.getBirthDate() == null || author.getNationality() == null)
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Nome, data de nascimento e nacionalidade devem ser validos");
    }

    public void verifyDuplicatedAuthor() {

        boolean condition = repository.existsAuthorByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality());
        if (condition)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um autor cadastrado com esses parâmetros");
    }

    public void verifyReferencedAuthor() {

        List<Book> bookList = author.getBookList();
        if (!bookList.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O autor ainda possui livros e não pode ser deletado");
    }

    public void verifyNotFound() {

        if (author == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado no banco de dados");

    }
}
