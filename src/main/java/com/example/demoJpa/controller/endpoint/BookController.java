package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("{bookId}")
    public ResponseEntity<BookOutputDTO> getBookById(@PathVariable("bookId") Integer bookId) {

        BookOutputDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<Object> searchBook() {
//        return null;
//    }

    @PostMapping
    public ResponseEntity<Void> postBook(@RequestBody @Valid BookInputDTO book) {

        Integer id = bookService.persistBook(book);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
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
