package com.example.demoJpa.controller;

import com.example.demoJpa.domain.Author;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {

        this.repository = repository;
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable("id") Integer id) {

        return repository.findById(id).orElse(null);
    }

    @GetMapping("author/{id}")
    public Author getAuthorBookId(@PathVariable("id") Integer id) {

        return repository.findById(id).map(Book::getAuthor).orElse(null);
    }

    @PostMapping
    public void postBook(@RequestBody Book book) {

        repository.save(book);

    }
}
