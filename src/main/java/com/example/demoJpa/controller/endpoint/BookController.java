package com.example.demoJpa.controller.endpoint;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.BookRepository;
import com.example.demoJpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> postBook(@RequestBody @Valid BookInputDTO book) {

        Integer id = bookService.persistBook(book);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }



//    @GetMapping("{id}")
//    public Book getBookById(@PathVariable("id") Integer id) {
//
//        return repository.findById(id).orElse(null);
//    }
//
//    @GetMapping("author/{id}")
//    public Author getAuthorBookId(@PathVariable("id") Integer id) {
//
//        return repository.findById(id).map(Book::getAuthor).orElse(null);
//    }
}
