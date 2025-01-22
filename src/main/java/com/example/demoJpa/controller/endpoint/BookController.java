package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.GenericController;
import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;

    @GetMapping("{bookId}")
    public ResponseEntity<BookOutputDTO> getBookById(@PathVariable("bookId") Integer bookId) {

        BookOutputDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> searchBook(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) LocalDate publishDate,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        BookInputDTO search = new BookInputDTO(isbn, title, publishDate, genre, price, authorId);
        return new ResponseEntity<>(bookService.searchBook(search, size, page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> postBook(@RequestBody @Valid BookInputDTO book) {

        Integer id = bookService.persistBook(book);

        return ResponseEntity.created(generateUri(id)).build();
    }

    @PutMapping("{bookId}")
    public ResponseEntity<Void> putBook(
            @PathVariable("bookId") Integer bookId,
            @RequestBody @Valid BookInputDTO book) {

        bookService.updateBook(book, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Integer bookId) {

        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
