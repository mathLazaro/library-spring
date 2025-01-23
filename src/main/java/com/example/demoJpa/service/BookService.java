package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.controller.mapper.BookMapper;
import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import com.example.demoJpa.repository.BookRepository;
import com.example.demoJpa.validator.BookValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final BookMapper bookMapper;

    @Transactional
    public BookOutputDTO getBookById(Integer id) {

        Book book = bookRepository.findById(id).orElse(null);
        bookValidator.verifyNotFound(book);
        return bookMapper.toBookOutputDTO(book);
    }

    public Page<BookOutputDTO> searchBook(String title,
                                          String isbn,
                                          String authorName,
                                          String genre,
                                          BigDecimal price,
                                          Integer year,
                                          Integer size,
                                          Integer page) {
        // TODO - optimizar busca com Specifications Criteria
        PageRequest pageRequest = PageRequest.of(page, size);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnorePaths("lastModifiedBy", "createdDate", "lastModifiedDate", "id", "author.id")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Author author = Author.builder()
                .name(authorName)
                .build();

        Book book = Book.builder()
                .isbn(isbn)
                .author(author)
                .title(title)
                .price(price)
                .genre(genre)
                .build();

        Example<Book> example = Example.of(book, exampleMatcher);

        Page<BookOutputDTO> response = bookRepository.findAll(example, pageRequest).map(bookMapper::toBookOutputDTO);
        if (year != null) {
            List<BookOutputDTO> content = response.getContent().stream().filter(b -> b.publishDate().getYear() == year).toList();
            return new PageImpl<>(content);
        }
        return response;
    }

    public Integer persistBook(BookInputDTO bookInput) {

        if (!authorRepository.existsById(bookInput.authorId()))
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

        Book book = bookMapper.toBook(bookInput);
        bookValidator.verifyDuplicatedISBN(book);
        bookValidator.verifyPriceNullableCondition(book);

        return bookRepository.save(book).getId();
    }

    @Transactional
    public void updateBook(BookInputDTO bookInput, Integer bookId) {


        Book bookOnDB = bookRepository.findById(bookId).orElse(null);
        bookValidator.verifyNotFound(bookOnDB);

        Book bookUpdate = bookMapper.toBook(bookInput);
        bookValidator.verifyPriceNullableCondition(bookUpdate);

        if (bookUpdate.getAuthor() == null)
            throw new EntityNotFoundException("Id não encontrado no banco de dados");

        // verifica o isbn setado caso seja modificado
        if (!bookUpdate.getIsbn().equals(bookOnDB.getIsbn())) {
            bookValidator.verifyDuplicatedISBN(bookUpdate);
            bookOnDB.setIsbn(bookInput.isbn());
        }
        bookOnDB.setTitle(bookUpdate.getTitle());
        bookOnDB.setGenre(bookUpdate.getGenre());
        bookOnDB.setPrice(bookUpdate.getPrice());
        bookOnDB.setPublishDate(bookUpdate.getPublishDate());
        bookOnDB.setAuthor(bookUpdate.getAuthor());
    }

    public void deleteBook(Integer id) {

        if (!bookRepository.existsById(id))
            throw new EntityNotFoundException("Id não encontrado no banco de dados");
        bookRepository.deleteById(id);
    }
}
