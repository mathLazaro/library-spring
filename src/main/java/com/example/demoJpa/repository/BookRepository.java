package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsBookByIsbn(String isbn);
}
