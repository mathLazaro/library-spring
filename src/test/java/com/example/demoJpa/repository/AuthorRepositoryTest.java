package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for AuthorRepository")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("Valid Author parameter is saved on database")
    public void saveTest() {

        Author author = new Author();
        author.setName("Test Name");

        Author response = repository.save(author);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(response.getName(), author.getName());
    }

    @Test
    @DisplayName("Valid id parameter deletes correspondent Author on database")
    public void deleteTest() {

        Author author = new Author();
        author.setName("Test Name");

        Author savedAuthor = repository.save(author);
        repository.delete(savedAuthor);
        Optional<Author> response = repository.findById(savedAuthor.getId());

        Assertions.assertTrue(response.isEmpty());
    }
}
