package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    public List<Author> findAuthorsByNameContainingAndNationalityContaining(String name, String nationality);
    public List<Author> findAuthorsByNameContaining(String name);
    public List<Author> findAuthorsByNationalityContaining(String nationality);
    public boolean existsAuthorByNameAndBirthDateAndNationality(String name, LocalDate birthDate, String nationality);
}
