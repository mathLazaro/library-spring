package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.controller.mapper.BookMapper;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import com.example.demoJpa.repository.BookRepository;
import com.example.demoJpa.validator.BookValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // TODO - optimizar

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
