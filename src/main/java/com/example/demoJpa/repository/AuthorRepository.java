package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    boolean existsAuthorByNameAndBirthDateAndNationality(String name, LocalDate birthDate, String nationality);
    Author findFirstByName(String name);
}
