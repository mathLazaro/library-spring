package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.service.AuthorService;
import com.example.demoJpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;

    @GetMapping("{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<BookOutputDTO> getBookById(@PathVariable("bookId") Integer bookId) {

        BookOutputDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Object> searchBook(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        Page<BookOutputDTO> bookOutputDTOPage = bookService.searchBook(title, isbn, authorName, genre, price, year, size, page);
        return new ResponseEntity<>(bookOutputDTOPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Void> postBook(@RequestBody @Valid BookInputDTO book) {

        Integer id = bookService.persistBook(book);

        return ResponseEntity.created(generateUri(id)).build();
    }

    @PutMapping("{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Void> putBook(
            @PathVariable("bookId") Integer bookId,
            @RequestBody @Valid BookInputDTO book) {

        bookService.updateBook(book, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Integer bookId) {

        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
