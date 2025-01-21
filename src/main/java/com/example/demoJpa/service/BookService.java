package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.mapper.BookMapper;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import com.example.demoJpa.repository.BookRepository;
import com.example.demoJpa.validator.BookValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    public Integer persistBook(BookInputDTO bookInput) {

        if (!authorRepository.existsById(bookInput.authorId()))
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

        Book book = bookMapper.toBook(bookInput);
        bookValidator.verifyDuplicatedISBN(book);
        bookValidator.verifyPriceNullableCondition(book);

        return bookRepository.save(book).getId();
    }
}
