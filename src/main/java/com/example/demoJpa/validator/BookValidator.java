package com.example.demoJpa.validator;

import com.example.demoJpa.domain.Book;
import com.example.demoJpa.exception.InvalidCheckException;
import com.example.demoJpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BookValidator extends EntityValidator<Book> {

    private final BookRepository bookRepository;

    public void verifyDuplicatedISBN(Book book) {

        if (bookRepository.existsBookByIsbn(book.getIsbn()))
            throw new DuplicateKeyException("Já existe um livro cadastrado com esse ISBN");
    }

    public void verifyPriceNullableCondition(Book book) {

        LocalDate conditionDate = LocalDate.of(2020, 1, 1);
        if ((book.getPublishDate().isAfter(conditionDate) || book.getPublishDate().isEqual(conditionDate)) && (book.getPrice() == null))
            throw new InvalidCheckException("Para livros lançados depois de 2020, o preço deve ser obrigatoriamente fornecido");

    }
}
